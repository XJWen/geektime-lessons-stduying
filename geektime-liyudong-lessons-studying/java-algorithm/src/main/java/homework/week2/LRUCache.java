package homework.week2;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRUCache {

    private int size;

    private Node head,tail;


    private Map<Integer,Node> cache = new ConcurrentHashMap<>();

    LRUCache(int capacity) {
        this.size = capacity;
        this.head = null;
        this.tail = null;
    }

    // 获取缓冲区中 key 对应的 value
    int get(int key) {
        // 1.当该 key 值存在
        if(cache.size() > 0)
        {
            // 删除该 key 对应的原来节点
            Node cur = cache.get(key);
            int value = cur.value;
            remove(cur);   // 这里仅仅删除哈希双向链表中的节点，不必删除哈希表中的
            // 将节点重现插入到缓冲区的头部
            setHead(cur);
            return value;
        }
        // 2.当该 key 值不存在
        return -1;
    }
    // 将key-value值存入缓冲区
    void put(int key, int value) {
        // 1.当该 key 值存在
        if(cache.size() > 0)
        {
            // 删除该 key 对应的原来节点
            Node cur = cache.get(key);
            cur.value = value;
            remove(cur);    // 这里仅仅删除哈希双向链表中的节点，不必删除哈希表中的
            // 将节点重现插入到缓冲区的头部
            setHead(cur);
        }
        else
        {
            // 2.当该 key 值不存在
            Node node = new Node(key, value);
            // 判断当前缓冲区大小已经满了
            if(cache.size() >= size)
            {
                // 删除尾部节点
                remove(tail);
            }
            //else 此时因为动作和上面重复，所以直接合并使用
            //还没有满：将新节点插入到缓冲区的头部

                setHead(node);
                cache.put(key, node);

        }
    }

    // 删除当前节点
    void remove(Node cur)
    {
        // 当前节点是 head
        if(cur == head) {
            head = cur.next;
        }else if(cur == tail) {// 当前节点是 tail
            tail = cur.prev;
        }else
        {
            // 当前节点是一般节点
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
        }
    }
    // 将当前节点插入到头部
    void setHead(Node cur)
    {
        cur.next = head;
        if(head != null){
            head.prev = cur;
        }
        head = cur;//重现更新head

        if(tail==null){
            tail = head;
        }
    }



    class Node {
        public int key,value;

        public Node prev,next;

        public Node(int k, int v) {
            this.key = k;
            this.value = v;
        }

    }

}
