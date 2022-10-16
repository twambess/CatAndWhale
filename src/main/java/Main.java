import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure()
                .addAnnotatedClass(Animal.class)
                .buildSessionFactory();
        System.out.println("Правила игры:\n1 - Да\n2 - Нет\n");

        Session session = factory.getCurrentSession();
        int m = 0;
        int ans = 0;
        System.out.println("Чтобы играть введите 1: ");
        Scanner sc = new Scanner(System.in);
        m = sc.nextInt();
        session.beginTransaction();
        while (m == 1) {
            List<Animal> list = session.createQuery("from Animal").getResultList();
            List<Animal> list2 = session.createQuery("from Animal").getResultList();


            while (list.size() > 1) {
                boolean bol = true;
                for (Animal a : list) {

                    if (bol) {
                        System.out.println("This animal " + a.getJudgment() + "?");
                        ans = sc.nextInt();

                        if (ans == 1) {
                            Animal currAn = session.get(Animal.class, a.getId());

                            list = session.createQuery("from Animal where animalOne='"
                                    + currAn.getAnimalOne()
                                    + "' or animalTwo='"
                                    + currAn.getAnimalOne() + "'").getResultList();
                            session.delete(currAn);


                            if (list.size() == 1) {

                                System.out.print("Your animal is " + currAn.getAnimalOne() + "?");
                                Scanner in1 = new Scanner(System.in);
                                int k = in1.nextInt();
                                if (k == 1) {
                                    System.out.println("Do u wanna play more?");
                                    m = sc.nextInt();
                                }

                                if (k == 2) {
                                    Scanner in = new Scanner(System.in);
                                    System.out.print("What is animal did you think of?");
                                    String s = in.nextLine();
                                    System.out.print("How is it different from " + currAn.getAnimalOne() + "?");
                                    String d = in.nextLine();
                                    Animal newAnimal = new Animal(s, currAn.getAnimalOne(), d);
                                    list2.add(newAnimal);
                                    System.out.println("Do u wanna play more?");
                                    m = sc.nextInt();
                                    if (m != 1) {
                                        session.save(newAnimal);
                                        session.getTransaction().commit();
                                        factory.close();
                                        System.exit(200);
                                    }
                                    bol = false;
                                }

                            }

                        }
                        if (ans == 2) {
                            Animal currAn = session.get(Animal.class, a.getId());

                            list = session.createQuery("from Animal where animalTwo='"
                                    + currAn.getAnimalTwo()
                                    + "' or animalOne='"
                                    + currAn.getAnimalTwo() + "'").getResultList();
                            session.delete(currAn);


                            if (list.size() == 1) {
                                System.out.print("Your animal is " + currAn.getAnimalTwo() + "?");
                                int k = sc.nextInt();
                                if (k == 1) {
                                    System.out.println("Do u wanna play more?");
                                    m = sc.nextInt();
                                    bol = false;
                                }

                                if (k == 2) {
                                    Scanner in = new Scanner(System.in);
                                    System.out.println("What is animal did you think of?");
                                    String s = in.nextLine();
                                    System.out.println("How is it different from " + currAn.getAnimalTwo() + "?");
                                    String d = in.nextLine();
                                    Animal newAnimal = new Animal(s, currAn.getAnimalTwo(), d);
                                    list2.add(newAnimal);
                                    System.out.println("Do u wanna play more?");
                                    m = sc.nextInt();
                                    if (m != 1) {
                                        session.save(newAnimal);
                                        session.getTransaction().commit();
                                        factory.close();
                                        System.exit(200);
                                    }
                                    bol = false;
                                }
                            }
                            session.delete(a);
                            list.remove(a);
                        }

                    }
                }
            }

            list2.forEach(session::save);

        }

        session.getTransaction().commit();
        factory.close();

    }
}