package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commom.Result;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    BookMapper BookMapper;

    // 简单实现的线性表
    static class CustomList<T> {
        private Object[] elements; // 存储元素的数组
        private int size; // 当前元素个数
        private static final int DEFAULT_CAPACITY = 1000; // 默认初始容量

        public CustomList() {
            elements = new Object[DEFAULT_CAPACITY]; // 初始化数组
            size = 0; // 初始元素个数为0
        }

        public void add(T element) {
            ensureCapacity(size + 1); // 确保容量足够
            elements[size++] = element; // 将元素添加到数组末尾
        }

        public T get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            return (T) elements[index]; // 获取指定索引位置的元素
        }

        public int size() {
            return size; // 返回当前元素个数
        }

        private void ensureCapacity(int minCapacity) {
            int oldCapacity = elements.length; // 原始容量
            if (minCapacity > oldCapacity) {
                int newCapacity = oldCapacity + (oldCapacity >> 1); // 扩容为原来的1.5倍
                if (newCapacity < minCapacity) {
                    newCapacity = minCapacity; // 若计算得到的新容量小于所需最小容量，则直接使用所需最小容量
                }
                Object[] newElements = new Object[newCapacity]; // 创建新的数组
                System.arraycopy(elements, 0, newElements, 0, size); // 将原始数组中的元素复制到新数组
                elements = newElements; // 更新数组引用
            }
        }
    }

    // 保存书籍信息
    @PostMapping
    public Result<?> save(@RequestBody Book book) {
        BookMapper.insert(book);
        return Result.success();
    }

    // 更新书籍信息
    @PutMapping
    public Result<?> update(@RequestBody Book Book) {
        BookMapper.updateById(Book);
        return Result.success();
    }

    // 批量删除
    //CustomList<T> 类实现了一个简单的线性表，使用数组 elements 存储元素，并通过 size 变量跟踪当前元素的个数
    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody CustomList<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            BookMapper.deleteById(ids.get(i));
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        BookMapper.deleteById(id);
        return Result.success();
    }

    //LambdaQueryWrapper<Book> 对象来构建查询条件，并利用了的哈希索引实现高效的数据检索
    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search1,
            @RequestParam(defaultValue = "") String search2,
            @RequestParam(defaultValue = "") String search3
    ) {
        LambdaQueryWrapper<Book> wrappers = Wrappers.<Book>lambdaQuery();
        if (StringUtils.isNotBlank(search1)) {
            wrappers.like(Book::getIsbn, search1);
        }
        if (StringUtils.isNotBlank(search2)) {
            wrappers.like(Book::getName, search2);
        }
        if (StringUtils.isNotBlank(search3)) {
            wrappers.like(Book::getAuthor, search3);
        }
        Page<Book> BookPage = BookMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        return Result.success(BookPage);
    }
}
