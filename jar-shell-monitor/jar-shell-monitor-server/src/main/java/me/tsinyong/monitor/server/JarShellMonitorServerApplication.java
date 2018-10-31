package me.tsinyong.monitor.server;

import me.tsinyong.monitor.server.listener.IServerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@SpringBootApplication
public class JarShellMonitorServerApplication implements CommandLineRunner {

    @Autowired
    private IServerListener iServerListener;

    private Logger logger = LoggerFactory.getLogger(JarShellMonitorServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JarShellMonitorServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        iServerListener.startServer();
    }

    @Scheduled(fixedRate = 10000)
    public void schema() {
        logger.info("working");
    }
}
