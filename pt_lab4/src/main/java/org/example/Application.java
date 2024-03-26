package org.example;

import org.example.Models.Mage;
import org.example.Models.Tower;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.example.repositories.MageRepository;
import org.example.repositories.TowerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Hello world!");
    }

    @Bean
    public CommandLineRunner DataOperations(MageRepository mageRepository, TowerRepository towerRepository) {
        return (args) -> {
            List<Tower> towers = generateTowers(5);
            List<Mage> mages = generateMages(5, towers);


            towerRepository.saveAll(towers);

            mageRepository.saveAll(mages);

            towerRepository.findAll().forEach(tower -> {
                List<Mage> m = mages.stream().filter(mg -> Objects.equals(mg.getTower().getName(), tower.getName())).toList();
                tower.setMages(m);
                towerRepository.save(tower);
            });
//            towers.forEach(tower -> {
//                var m = mages.stream().filter(mage -> mage.getTower() == tower).toList();
//                tower.setMages(m);
//            });
//            towerRepository.saveAll(towers);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                switch (line) {
                    case "q": return;
                    case "g":
                        System.out.println("Mages:");
                        mageRepository.findAll().forEach(System.out::println);
                        towerRepository.findAll().forEach(System.out::println);
                        break;
                    case "dm":
                        line = scanner.nextLine();
                        String finalLine = line;
                        towerRepository.findAll().forEach(t -> {
                            t.setMages(t.getMages().stream().filter(m -> !m.getName().equals(finalLine)).toList());
                            towerRepository.save(t);
                        });
                        mageRepository.deleteById(line);
                        break;
                    case "dt":
                        line = scanner.nextLine();
//                        towerRepository.findById(line).ifPresent(t -> {
//                            t.getMages().forEach(m -> {
//                                m.setTower(null);
//                                mageRepository.save(m);
//                            });
//                        });
                        towerRepository.deleteById(line);
                        break;
                    case "am":
                        Mage mage = new Mage();
                        mage.setName(scanner.nextLine());
                        mage.setLevel(Integer.parseInt(scanner.nextLine()));
                        line = scanner.nextLine();
                        Tower t = towerRepository.findById(line).orElse(null);
                        if (t != null) {
                            t.getMages().add(mage);
                            towerRepository.save(t);
                            mage.setTower(t);
                            mageRepository.save(mage);
                        }
                        break;
                    case "wt":
                        int l = scanner.nextInt();
                        mageRepository.findMagesWeakerThan(l).forEach(System.out::println);
                        break;
                }
            }
        };
    }

    public static List<Mage> generateMages(int count, List<Tower> towers) {
        List<Mage> mages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Mage mage = new Mage();
            mage.setName("Mage " + (i + 1));
            mage.setLevel((int) (Math.random() * 100));
            int index = (int) (Math.random() * towers.size());
            Tower tower = towers.get(index);
            mage.setTower(tower);

            mages.add(mage);


        }

        return mages;
    }

    public static  List<Tower> generateTowers(int count) {
        List<Tower> towers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Tower tower = new Tower();
            tower.setName("Tower " + (i + 1));
            tower.setHeight((int) (Math.random() * 100));

            towers.add(tower);
        }

        return towers;
    }
}