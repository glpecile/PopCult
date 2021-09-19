package ar.edu.itba.paw.models;

import java.util.List;

public class PageContainer<T> {
    private final List<T> elements;
    private final int currentPage;
    private final int pageSize;
    private final int totalCount;

    public PageContainer(List<T> elements, int currentPage, int pageSize, int totalCount) {
        this.elements = elements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public List<T> getElements() {
        return elements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalPages(){
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}
