package utils;

import collectionManagers.CollectionManager;
import collectionManagers.StorageManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.Iterator;

import static utils.CommandManager.handlePacket;
import static utils.CommandManager.setChannel;

public class Server {
    private final int port;
    private DatagramChannel datagramChannel;
    private Selector selector;
    private CollectionManager collectionManager;
    private StorageManager storageManager;

    public Server(CollectionManager collectionManager, int port, StorageManager storageManager) {
        this.collectionManager = collectionManager;
        this.port = port;
        this.storageManager = storageManager;
    }
    BufferedReader scanner = new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)));


    public void run() throws InterruptedException {
        try {
            Thread consoleThread = new Thread(() -> {
                while (true){
                    try {
                        if (scanner.ready()) {
                            String line = scanner.readLine();
                            if (line.equals("save")) {
                                storageManager.writeCollection();
                            }
                            if (line.equals("exit")) {
                                storageManager.writeCollection();
                                ServerLogger.getLogger().info("Работа сервера завершена");
                                System.exit(0);
                            }
                        }
                    } catch (Exception ignored) {}
                }
            });
            datagramChannel = DatagramChannel.open();
            datagramChannel.socket().bind(new InetSocketAddress(port));
            datagramChannel.configureBlocking(false);
            selector = Selector.open();
            datagramChannel.register(selector, SelectionKey.OP_READ);
            ServerLogger.getLogger().info("Сервер запущен на порте " + port);
            HashMap<InetSocketAddress, ByteArrayOutputStream> byteStreams = new HashMap<>();
            Thread clientThread = new Thread(() -> {
                while (true) {
                    try {
                        selector.select();
                        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                        while (keys.hasNext()) {
                            SelectionKey key = keys.next();
                            keys.remove();
                            if (!key.isValid()) {
                                continue;
                            }
                            if (key.isReadable()) {
                                DatagramChannel keyChannel = (DatagramChannel) key.channel();
                                keyChannel.configureBlocking(false);
                                setChannel(keyChannel);
                                ByteBuffer buffer = ByteBuffer.allocate(1025);
                                InetSocketAddress inetSocketAddress = (InetSocketAddress) keyChannel.receive(buffer);
                                ByteArrayOutputStream byteStream = byteStreams.get(inetSocketAddress);
                                if (byteStream == null) {
                                    byteStream = new ByteArrayOutputStream();
                                    byteStreams.put(inetSocketAddress, byteStream);
                                }
                                boolean hasNext = buffer.array()[buffer.limit() - 1] == 1;
                                byteStream.write(buffer.array(), 0, buffer.limit() - 1);
                                if (!hasNext) {
                                    try {
                                        handlePacket(inetSocketAddress, byteStream.toByteArray());
                                    } catch (Exception e) {
                                        keyChannel.send(ByteBuffer.wrap("ERROR: Упс! Что-то пошло не по плану".getBytes()), inetSocketAddress);
                                        ServerLogger.getLogger().warning(e.toString());
                                    }
                                    byteStreams.remove(inetSocketAddress);
                                }
                                buffer.clear();
                            }
                        }
                    } catch (Exception e){
                        ServerLogger.getLogger().warning("Ошибка: " + e.getMessage());
                    }
                }
            });
            consoleThread.start();
            clientThread.start();
            consoleThread.join();
            clientThread.join();
        } catch (IOException | InterruptedException e) {
            ServerLogger.getLogger().warning("Ошибка: " + e.getMessage());
        } finally {
            try {
                selector.close();
                datagramChannel.close();
            } catch (IOException e) {
                ServerLogger.getLogger().warning("Не получилось закрыть канал или селектор: " + e.getMessage());
            }
       }
    }
}