package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("digite um número:: ");
            int a = sc.nextInt();

            System.out.println("digite um outro valor: ");
            int b = sc.nextInt();

            System.out.println("Aqui esta seu resultado: ");
            System.out.println(a * b);
        }
    }
}