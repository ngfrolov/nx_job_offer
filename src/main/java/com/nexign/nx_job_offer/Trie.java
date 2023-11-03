package com.nexign.nx_job_offer;

/**
 * Префиксное дерево.
 * @author Nikolay Frolov
 */
public class Trie {

    /**
     * Корень префиксного дерева. В качестве метки использовано Character.MIN_VALUE
     */
    private TrieNode root = new TrieNode(Character.MIN_VALUE, null, null);

    /**
     * Добавление слова в префиксное дерево
     * @param word добавляемое слово
     * @param index индекс слова в файла
     * @return вершина, ассоциированная с добавленным словом. Соответствует последнему символу слова.
     */
    public TrieNode addWord(CharSequence word, Integer index) {
        return walkThrough(true, word, index);
    }

    /** Поиск слова в префиксном дереве
     * @param word искомое слово
     * @return вершина, ассоциированная с добавленным словом. Соответствует последнему символу слова.
     */
    public TrieNode findWord(CharSequence word) {
        return walkThrough(false, word, null);
    }

    /**
     * Метод прохода по вершинам дерева. В зависимости от флага toAdd используется для добавления слова в дерева
     * и для поиска слова по дереву (без добавления).
     * @param toAdd флаг, определяющий действие - добавление слова, поиск без добавления.
     * @param word слово
     * @param index индекс слова в файле. Учитывается только при установленном флаге toAdd.
     *              При поиске без добавления игнорируется.
     * @return вершина префиксного дерева, соответствующая заданному слову.
     */
    private TrieNode walkThrough(boolean toAdd, CharSequence word, Integer index) {
        TrieNode currNode = root;
        // Последовательно идем по символам слова
        for (int i = 0; i < word.length(); i++) {
            // Получаем потомка текущей вершины, метка которого совпадает с очередным символом слова.
            TrieNode nextNode = currNode.getChild(word.charAt(i));
            // Если такого потомка нет.
            if (nextNode == null) {
                if (toAdd) {
                    // Если установлен флаг добавления, создаем новую вершину .
                    // Если текущий символ слова - не последний, а транзитный, новая вершина не имеет индекса.
                    // Если текущий символ слова - последний, записываем в вершину индекс слова.
                    nextNode = new TrieNode(word.charAt(i), currNode,
                            i < word.length() - 1 ?  null : index);
                    // Добавляем новую вершину в качестве потомка к текущей вершине.
                    currNode.addChild(nextNode);
                } else {
                    return null;
                }
            } else {
                // В эту ветку мы попаем только если заданное слово уже встречалось в файле,
                // для этого слова была создана вершина с некоторым индексом.
                // Если установлен флаг добавления, добавляем ещё один индекс.
                if (toAdd && i == word.length() - 1) {
                    nextNode.addIndex(index);
                }
            }
            currNode = nextNode;
        }
        return currNode;
    }

}
