package com.nexign.nx_job_offer;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * Индекс по словам внутри файла.
 * @author Nikolay Frolov
 */
public class WordIndex {
    /** Префиксное дерево. Каждый раз при вызове метода loadFile инициализируется заново. */
    private Trie trie;

    /** Символы-разделители слов в файле.
     * Поле уровня экземпляра, а не уровня класса, потому как в разных файлах могут быть разные разделители. */
    private String delimiters = " \n\r";

    /** Кодировка файла.
     * * Поле уровня экземпляра, а не уровня класса, потому как в разных файлах могут быть разные кодировки */
    private Charset encoding = StandardCharsets.UTF_8;

    /**
     * Загрузка файла и построение индекса.
     * Примечание: сигнатура метода определена заданием, однако
     * целесообразным может быть указание в данном методе символа-разделителя для слов в файле.
     * В текущей реализации символы-разделители заданы в поле delimeters и не подлежат изменению.
     * @param fileName имя файла
     * @throws IOException
     */
    public void loadFile(String fileName) throws IOException {
        trie = new Trie();

        try (InputStream fin = new FileInputStream(fileName);
             BufferedInputStream bin = new BufferedInputStream(fin);
             Reader reader = new InputStreamReader(bin, encoding)) {

            StringBuilder newWord = new StringBuilder();
            int wordIndex = 0;
            int nextSym;
            // Читаем посимвольно файл.
            // Если текущий символ - разделитель или конец файла, проверяем, читали ли мы какое-то слово,
            // (или просто было несколько разделителей подряд).
            // Если слово было - добавляем его в префиксное дерево.
            // и обнуляем текущее слово.
            do {
                nextSym = reader.read();
                if (delimiters.indexOf(nextSym) >= 0 || nextSym < 0) {
                    if (newWord.length() > 0) {
                        trie.addWord(newWord, ++wordIndex);
                        newWord = new StringBuilder();
                    }
                } else {
                    // Не разделитель и не конец файла - продолжаем читать текущее слово.
                    // Поскольку по условиям задания кодировка UTF-8, такое приведение типа
                    // является безопасным. Однако, в случае использования иных кодировок
                    // возможны проблемы.
                    newWord.append((char) nextSym);
                }
            } while (nextSym >= 0);
        }

    }

    /**
     * Получение множества индексов слова внутри файла.
     * @param searchWord искомое слово
     * @return множество индексов. Нумерация слов в файле начинается с единицы.
     */
    public Set<Integer> getIndexes4Word(String searchWord) {
        TrieNode node = trie.findWord(searchWord);
        return node != null ? node.getIndexes() : null;
    }
}
