
package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commom.Result;
import com.example.demo.entity.BookWithUser;
import com.example.demo.mapper.BookWithUserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookwithuser")
public class BookWithUserController {

    // 简单实现的线性表
    static class CustomList<T> {
        private Object[] elements; // 存储元素的数组
        private int size; // 当前元素数量
        private static final int DEFAULT_CAPACITY = 1000; // 默认容量

        /**
         * 默认构造函数，创建具有默认容量的 CustomList
         */
        public CustomList() {
            elements = new Object[DEFAULT_CAPACITY];
            size = 0;
        }

        /**
         * 向 CustomList 添加元素
         * @param element 要添加的元素
         */
        public void add(T element) {
            ensureCapacity(size + 1); // 确保容量足够
            elements[size++] = element; // 将元素添加到数组末尾，并更新元素数量
        }

        /**
         * 获取指定索引位置的元素
         * @param index 索引值
         * @return 索引位置的元素
         * @throws IndexOutOfBoundsException 如果索引超出范围，则抛出 IndexOutOfBoundsException 异常
         */
        public T get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            return (T) elements[index]; // 将 Object 类型的元素强制转换为泛型类型 T，并返回
        }

        /**
         * 移除指定索引位置的元素
         * @param index 索引值
         * @throws IndexOutOfBoundsException 如果索引超出范围，则抛出 IndexOutOfBoundsException 异常
         */
        public void remove(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            // 将索引位置之后的元素向前移动一位
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
            elements[--size] = null; // 将最后一个元素置为 null，并更新元素数量
        }

        /**
         * 获取 CustomList 中的元素数量
         * @return 元素数量
         */
        public int size() {
            return size;
        }

        /**
         * 确保 CustomList 的容量至少为指定的最小容量
         * @param minCapacity 最小容量
         */
        private void ensureCapacity(int minCapacity) {
            int oldCapacity = elements.length; // 当前容量
            if (minCapacity > oldCapacity) { // 如果指定的最小容量大于当前容量，则需要扩容
                int newCapacity = oldCapacity + (oldCapacity >> 1); // 新容量为当前容量的 1.5 倍
                if (newCapacity < minCapacity) {
                    newCapacity = minCapacity; // 如果计算得到的新容量仍然小于指定的最小容量，则直接使用最小容量
                }
                Object[] newElements = new Object[newCapacity]; // 创建新的数组
                System.arraycopy(elements, 0, newElements, 0, size); // 将旧数组中的元素复制到新数组中
                elements = newElements; // 更新数组引用
            }
        }
    }

    // 简单实现的哈希表
    static class CustomHashMap<K, V> {
        //buckets 是一个数组，用于存储元素 类型是 CustomList[]，也就是 CustomList 类型的数组
        private CustomList<Entry<K, V>>[] buckets; // 存储哈希表桶的数组，阻止哈希冲突
        private int capacity; // 哈希表容量
        private int size; // 哈希表中键值对的数量
        private static final int DEFAULT_CAPACITY = 1000; // 默认容量

        /**
         * 默认构造函数，使用默认容量创建哈希表
         */
        public CustomHashMap() {
            this(DEFAULT_CAPACITY);
        }

        /**
         * 构造函数，使用指定容量创建哈希表
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
         * 将键值对插入哈希表中
         * 如果键已存在，则更新对应的值；否则，创建新的键值对
         * @param key 键
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
         * @param key 键
         * @return 值，如果键不存在则返回null
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
         * 根据键从哈希表中删除对应的键值对
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
         * @return 键值对的数量
         */
        public int size() {
            return size;
        }

        /**
         * 根据键计算哈希值，并映射到桶的索引
         * @param key 键
         * @return 桶的索引
         */
        private int getIndex(K key) {
            return key.hashCode() % capacity;
        }

        /**
         * 哈希表中存储的键值对实体类
         */
        static class Entry<K, V> {
            private K key; // 键
            private V value; // 值

            /**
             * 构造函数，创建键值对实体
             * @param key 键
             * @param value 值
             */
            public Entry(K key, V value) {
                this.key = key;
                this.value = value;
            }

            /**
             * 获取键
             * @return 键
             */
            public K getKey() {
                return key;
            }

            /**
             * 获取值
             * @return 值
             */
            public V getValue() {
                return value;
            }

            /**
             * 设置值
             * @param value 值
             */
            public void setValue(V value) {
                this.value = value;
            }
        }

        //getIndex(K key)，用于根据键计算哈希值，并将其映射到桶的索引。每个桶是一个线性表，用于解决哈希冲突
    }
    @Resource
    BookWithUserMapper BookWithUserMapper;

    @PostMapping("/insertNew")
    public Result<?> insertNew(@RequestBody BookWithUser BookWithUser){
        BookWithUserMapper.insert(BookWithUser);
        return Result.success();
    }
    @PostMapping
    public Result<?> update(@RequestBody BookWithUser BookWithUser){
        UpdateWrapper<BookWithUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("isbn",BookWithUser.getIsbn()).eq("id",BookWithUser.getId());
        BookWithUserMapper.update(BookWithUser, updateWrapper);
        return Result.success();
    }
//删除一条记录
    // CustomHashMap<String, Object> 对象来存储书籍和用户信息的键值对。CustomHashMap 是我自己实现的哈希表类，它提供了插入、查找和删除等操作
    @PostMapping("/deleteRecord")
    public  Result<?> deleteRecord(@RequestBody BookWithUser BookWithUser){
        Map<String,Object> map = new HashMap<>();
        map.put("isbn",BookWithUser.getIsbn());
        map.put("id",BookWithUser.getId());
        BookWithUserMapper.deleteByMap(map);
        return Result.success();
    }

    @PostMapping("/deleteRecords")
    public Result<?> deleteRecords(@RequestBody List<BookWithUser> bookWithUsers) {
        CustomHashMap<String, Object> customHashMap = new CustomHashMap<>();
        for (BookWithUser bookWithUser : bookWithUsers) {
            customHashMap.put("isbn", bookWithUser.getIsbn());
            customHashMap.put("id", bookWithUser.getId());
            customHashMap.remove("isbn"); // 移除指定键的键值对
        }
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search1,
                              @RequestParam(defaultValue = "") String search2,
                              @RequestParam(defaultValue = "") String search3){
        LambdaQueryWrapper<BookWithUser> wrappers = Wrappers.<BookWithUser>lambdaQuery();
        if(StringUtils.isNotBlank(search1)){
            wrappers.like(BookWithUser::getIsbn,search1);
        }
        if(StringUtils.isNotBlank(search2)){
            wrappers.like(BookWithUser::getBookName,search2);
        }
        if(StringUtils.isNotBlank(search3)){
            wrappers.like(BookWithUser::getId,search3);
        }
        Page<BookWithUser> BookPage =BookWithUserMapper.selectPage(new Page<>(pageNum,pageSize), wrappers);
        return Result.success(BookPage);
    }
}
