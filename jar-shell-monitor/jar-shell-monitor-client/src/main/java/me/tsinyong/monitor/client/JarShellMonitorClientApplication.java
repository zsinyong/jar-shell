package me.tsinyong.monitor.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class JarShellMonitorClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JarShellMonitorClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {

        }
    }
}
