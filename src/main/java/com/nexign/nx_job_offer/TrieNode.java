package com.nexign.nx_job_offer;

import java.util.*;

/**
 * Узел префиксного дерева.
 * @author Nikolay Frolov
 */
public class TrieNode {
    private Character label;
    private Set<Integer> indexes;
    private Map<Character, TrieNode> children;
    private TrieNode parent;

    /**
     * Конструктор узла дерева.
     * @param label метка узла
     * @param parent родительский узел
     * @param index индекс слова, получаемого путем прохода от корня дерева к данной вершине.
     *              Если слово имеет несколько индексов, использовать метод addIndex(Integer index);
     *              Если index == null, значит вершина является транзитной и не связана со словом.
     */
    public TrieNode(Character label, TrieNode parent, Integer index) {
        this.label = label;
        this.parent = parent;
        addIndex(index);
    }

    /**
     * Получение символа (метки), который ассоциирован с данной вершиной.
     * @return символ
     */
    public Character getLabel() {
        return label;
    }

    /**
     * Заданеи символа (метки), который будет ассоциирован с данной вершиной.
     * @param label
     */
    public void setLabel(Character label) {
        this.label = label;
    }

    /**
     * Слово, формируемое проходом от корня дерева к данной вершине.
     * Примечание: в задании не используется, но наличие такого метода считаю логичным.
     * @return слово
     */
    public String buildWord() {
        StringBuilder sb = new StringBuilder();
        sb.append(getLabel());
        while (parent.getLabel() != Character.MIN_VALUE) {
            sb.append(parent.getLabel());
        }
        return sb.toString();
    }

    /**
     * Получение индексов (вхождений в файл в текущем примере) для слова, формируемого
     * путем прохода от корня дерева к данной вершине.
     * @return множество индексов. null - если индексы у слова отсутствуют.
     */
    public Set<Integer> getIndexes() {
        return indexes;
    }

    /**
     * Добавить индекс слова, получаемого путем прохода от корна дерева к данной вершине.
     * @param index индекс влова
     */
    public void addIndex(Integer index) {
        if (indexes == null) {
            indexes = new HashSet<>();
        }
        indexes.add(index);
    }

    /** Поиск по заданной метке потомка текущей вершины
     * @param label заданная метка
     * @return потомок. null - если такого потомка нет.
     */
    public TrieNode getChild(Character label) {
        return children != null ? children.get(label) : null;
    }

    /**
     * Добавить потомок к текущей вершине.
     * @param child потомок
     */
    public void addChild(TrieNode child) {
        if (children == null) {
            children = new HashMap<>();
        }
        children.put(child.label, child);
    }
}
