
package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.LoginUser;
import com.example.demo.commom.Result;
import com.example.demo.entity.Book;
import com.example.demo.entity.LendRecord;
import com.example.demo.entity.User;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.LendRecordMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    /**
     * 自定义动态数组实现的列表结构
     *
     * @param <T> 列表元素的类型
     */
    static class CustomList<T> {
        private Object[] elements;  // 存储列表元素的数组
        private int size;  // 列表的大小
        private static final int DEFAULT_CAPACITY = 10;  // 默认容量

        /**
         * 构造函数，初始化一个空列表
         */
        public CustomList() {
            elements = new Object[DEFAULT_CAPACITY];
            size = 0;
        }

        /**
         * 添加元素到列表末尾
         *
         * @param element 要添加的元素
         */
        public void add(T element) {
            ensureCapacity(size + 1);
            elements[size++] = element;
        }

        /**
         * 获取指定索引处的元素
         *
         * @param index 索引值
         * @return 索引处的元素
         * @throws IndexOutOfBoundsException 如果索引越界
         */
        public T get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            return (T) elements[index];
        }

        /**
         * 移除指定索引处的元素
         *
         * @param index 索引值
         * @throws IndexOutOfBoundsException 如果索引越界
         */
        public void remove(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
            elements[--size] = null;
        }

        /**
         * 获取列表的大小
         *
         * @return 列表的大小
         */
        public int size() {
            return size;
        }

        /**
         * 确保列表的容量满足最小要求
         *
         * @param minCapacity 最小容量
         */
        private void ensureCapacity(int minCapacity) {
            int oldCapacity = elements.length;
            if (minCapacity > oldCapacity) {
                int newCapacity = oldCapacity + (oldCapacity >> 1);
                if (newCapacity < minCapacity) {
                    newCapacity = minCapacity;
                }
                Object[] newElements = new Object[newCapacity];
                System.arraycopy(elements, 0, newElements, 0, size);
                elements = newElements;
            }
        }
    }

    /**
     * 自定义哈希表实现
     *
     * @param <K> 哈希表键的类型
     * @param <V> 哈希表值的类型
     */
    static class CustomHashMap<K, V> {
        private CustomList<Entry<K, V>>[] buckets;  // 哈希表的桶数组
        private int capacity;  // 哈希表的容量
        private int size;  // 哈希表中键值对的数量
        private static final int DEFAULT_CAPACITY = 16;  // 默认容量

        /**
         * 构造函数，使用默认容量初始化哈希表
         */
        public CustomHashMap() {
            this(DEFAULT_CAPACITY);
        }

        /**
         * 构造函数，使用指定容量初始化哈希表
         *
         * @param capacity 哈希表容量
         */
        public CustomHashMap(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.buckets = new CustomList[capacity];
            for (int i = 0; i < capacity; i++) {
                buckets[i] = new CustomList<>();
            }
        }

        /**
         * 将键值对添加到哈希表中
         *
         * @param key   键
         * @param value 值
         */
        public void put(K key, V value) {
            int index = getIndex(key);
            CustomList<Entry<K, V>> bucket = buckets[index];
            for (int i = 0; i < bucket.size(); i++) {
                Entry<K, V> entry = bucket.get(i);
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
            }
            bucket.add(new Entry<>(key, value));
            size++;
        }

        /**
         * 根据键获取对应的值
         *
         * @param key 键
         * @return 对应的值，如果键不存在则返回null
         */
        public V get(K key) {
            int index = getIndex(key);
            CustomList<Entry<K, V>> bucket = buckets[index];
            for (int i = 0; i < bucket.size(); i++) {
                Entry<K, V> entry = bucket.get(i);
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
            return null;
        }

        /**
         * 根据键从哈希表中移除对应的键值对
         *
         * @param key 键
         */
        public void remove(K key) {
            int index = getIndex(key);
            CustomList<Entry<K, V>> bucket = buckets[index];
            for (int i = 0; i < bucket.size(); i++) {
                Entry<K, V> entry = bucket.get(i);
                if (entry.getKey().equals(key)) {
                    bucket.remove(i);
                    size--;
                    return;
                }
            }
        }

        /**
         * 获取哈希表中键值对的数量
         *
         * @return 键值对的数量
         */
        public int size() {
            return size;
        }

        /**
         * 根据键计算索引值
         *
         * @param key 键
         * @return 索引值
         */
        private int getIndex(K key) {
            return key.hashCode() % capacity;
        }

        /**
         * 哈希表中键值对的数据结构
         *
         * @param <K> 键的类型
         * @param <V> 值的类型
         */
        static class Entry<K, V> {
            private K key;  // 键
            private V value;  // 值

            /**
             * 构造函数，创建一个键值对
             *
             * @param key   键
             * @param value 值
             */
            public Entry(K key, V value) {
                this.key = key;
                this.value = value;
            }

            /**
             * 获取键
             *
             * @return 键
             */
            public K getKey() {
                return key;
            }

            /**
             * 获取值
             *
             * @return 值
             */
            public V getValue() {
                return value;
            }

            /**
             * 设置值
             *
             * @param value 值
             */
            public void setValue(V value) {
                this.value = value;
            }
        }
    }
    @Resource
    private UserMapper userMapper;
    @Resource
    private LendRecordMapper lendRecordMapper;
    @Resource
    private BookMapper bookMapper;



    @GetMapping
    public  Result<?> dashboardrecords(){
        int visitCount = LoginUser.getVisitCount();
        QueryWrapper<User> queryWrapper1=new QueryWrapper();

        int userCount = userMapper.selectCount(queryWrapper1);
        QueryWrapper<LendRecord> queryWrapper2=new QueryWrapper();

        int lendRecordCount = lendRecordMapper.selectCount(queryWrapper2);
        QueryWrapper<Book> queryWrapper3=new QueryWrapper();

        int bookCount = bookMapper.selectCount(queryWrapper3);
        Map<String, Object> CustomHashMap = new HashMap<>();//键值对形式
        CustomHashMap.put("visitCount", visitCount);//放置visitCount到map中
        CustomHashMap.put("userCount", userCount);
        CustomHashMap.put("lendRecordCount", lendRecordCount);
        CustomHashMap.put("bookCount", bookCount);
        return Result.success(CustomHashMap);
    }
}
