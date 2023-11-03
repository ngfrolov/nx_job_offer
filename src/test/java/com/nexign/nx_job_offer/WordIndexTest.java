package com.nexign.nx_job_offer;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WordIndexTest {
    private WordIndex wi;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        wi = new WordIndex();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void loadFile() {
        try {
            String fileName = "d:\\Downloads\\База данных имен и фамилий в формате CSV\\russian_surnames.csv";
            File file = new File(fileName);
            long fileLength = file.length();
            long startTime = new Date().getTime();
            wi.loadFile(fileName);
            long finishTime = new Date().getTime();

            System.out.println("Тестовый файл \"" + fileName + "\" успешно загружен.");
            System.out.println("Размер файла: " + fileLength + " байт.");
            System.out.println("Индекс слов по файлу с использованием префиксного дерева построен за "
                    + (finishTime - startTime) + " миллисекунд.");
        }
        catch (IOException e) {
            System.out.println("Ошибка при загрузке тестового файла.");
        }

    }

    @org.junit.jupiter.api.Test
    void getIndexes4Word() {
        loadFile();

        String word = "Фролов";
        long startTime = new Date().getTime();
        Set<Integer> result = wi.getIndexes4Word(word);
        long finishTime = new Date().getTime();

        if (result == null) {
            System.out.println("Слово \"" + word + "\" отсутствует в файле.");
        } else {
            System.out.print("Слово \"" + word + "\" находится на позициях:");
            for (Integer index : result) {
                System.out.print(" " + index);
            }
            System.out.println(".");
            System.out.println("Поиск выполнени за " + (finishTime - startTime) + " миллисекунд.");
        }

    }
}