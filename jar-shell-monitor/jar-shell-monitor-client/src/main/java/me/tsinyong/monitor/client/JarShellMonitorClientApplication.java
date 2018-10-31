package me.tsinyong.monitor.client;

import me.tsinyong.monitor.client.listener.ClientConnectionWorker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.Scanner;

@SpringBootApplication
public class JarShellMonitorClientApplication implements CommandLineRunner {

    @Resource
    private ClientConnectionWorker clientConnectionWorker;

    public static void main(String[] args) {
        SpringApplication.run(JarShellMonitorClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        clientConnectionWorker.connectToServer();
        Scanner scanner = new Scanner(System.in);
        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.equals("exit")) {
                System.exit(0);
            } else {
                clientConnectionWorker.sendMessageToServer(line);
            }
        }
    }
}
