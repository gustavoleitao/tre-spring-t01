package br.ufrn.imd.treeleicao.common;

import java.util.Collection;

public class Page<T> {

    int page;
    int size;
    long total;
    Collection<T> itens;

    private Page(int page, int size, long total, Collection<T> itens) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.itens = itens;
    }

    public static <T> Page from(int page, int size, long total, Collection<T> itens){
        return new Page(page, size, total, itens);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setItens(Collection<T> itens) {
        this.itens = itens;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    public Collection<T> getItens() {
        return itens;
    }
}
