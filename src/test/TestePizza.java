package test;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestePizza {
    public static void main(String[] args) {
        TipoSabor normal = new TipoSabor(1L,"normal",0.40);
        Sabores calabresa= new Sabores(1L,normal,"Calabresa");
        List<Sabores> sabores= new ArrayList<>();
        sabores.add(calabresa);
        Pizza primeira = new Pizza(1L,1L,sabores,new Quadrado(20));

        System.out.println(String.valueOf( primeira.getCms()));
        System.out.println(String.valueOf( primeira.getPreco()));
        List<Pizza> pizzas = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        boolean r=true;
        while (r) {
            System.out.println("Forma:");
            System.out.println("1 - Circular");
            System.out.println("2 - Quadrada");
            System.out.println("3 - Triangular");

            int forma = sc.nextInt();

            Pizza nova = null;
            switch (forma) {
                case 1:
                    System.out.println("Raio em Cm:");
                    nova = new Pizza(1L,1L,sabores, new Circulo(sc.nextDouble()));
                    break;
                case 2:
                    System.out.println("Lado em Cm:");
                    nova = new Pizza(1L,1L,sabores, new Quadrado(sc.nextDouble()));
                    break;
                case 3:
                    System.out.println("Base em Cm:");
                    nova = new Pizza(1L,1L,sabores, new Triangulo(sc.nextDouble()));
                    break;
                case 0:
                    r=false;
                    break;
            }
            if (nova!=null) {
                pizzas.add(nova);
            }

        }
        for(Pizza pizza : pizzas){
            System.out.println(pizza.getForma().getNomeForma());
            System.out.println(String.format("%.2f",pizza.getCms()));
            System.out.println(String.format("%.2f",pizza.getPreco()));
        }

    }


}
