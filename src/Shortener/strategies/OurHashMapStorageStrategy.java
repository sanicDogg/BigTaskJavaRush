package com.javarush.test.level33.lesson15.big01.strategies;



public class OurHashMapStorageStrategy implements StorageStrategy {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;                             //начальный размер массива
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;                             // Коэффициент загрузки. Значение по умолчанию 0.75 является хорошим компромиссом
    private Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];                        //Массив типа Entry[], который является хранилищем ссылок на списки (цепочки) значений
    private int size;                                                                   //Количество элементов HashMap-а
    private int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR); //Предельное количество элементов, при достижении которого, размер хэш-таблицы увеличивается вдвое
    private float loadFactor = DEFAULT_LOAD_FACTOR; //опред. степень загруженности корзин, т.е. макс соотношение количества элементов хранимых в мапе и длины массива (кол-ва корзин), после достижения которого длина массива удваивается.


    int hash(Long k){
        return k.hashCode();
    }

    int indexFor(int hash, int length){
        return hash & (length-1);
    }

    Entry getEntry(Long key){
        if (size == 0) {
            return null;
        }
        int hash = (key == null) ? 0 : hash(key);
        for (Entry e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    void resize(int newCapacity){
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == (1 << 30)) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)Math.min(newCapacity * loadFactor, (1 << 30) + 1);
    }

    void transfer(Entry[] newTable){
        int newCapacity = newTable.length;
        for (Entry e : table) {
            while(null != e) {
                Entry next = e.next;
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
    }

    void addEntry(int hash, Long key, String value, int bucketIndex){
        if ((size >= threshold) && (null != table[bucketIndex])) {
            resize(2 * table.length);
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }
        createEntry(hash, key, value, bucketIndex);
    }

    void createEntry(int hash, Long key, String value, int bucketIndex){
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        size++;
    }


    @Override
    public boolean containsKey(Long key) {
        return getEntry(key)!=null;
    }

    @Override
    public boolean containsValue(String value) {

        if (value == null)
            return false;
        for (Entry aTable : table) {
            for (Entry e = aTable; e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        addEntry(hash(key), key, value, indexFor(hash(key),table.length));
    }

    @Override
    public Long getKey(String value) {

        if (value == null)
            return 0l;
        for (Entry aTable : table) {
            for (Entry e = aTable; e != null; e = e.next)
                if (value.equals(e.value))
                    return aTable.getKey();
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        return null == getEntry(key) ? null : getEntry(key).getValue();
    }
}
