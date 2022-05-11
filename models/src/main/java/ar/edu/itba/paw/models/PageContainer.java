package ar.edu.itba.paw.models;

import java.util.List;

public class PageContainer<T> {
    private final List<T> elements;
    private final int currentPage;
    private final int pageSize;
    private final long totalCount;

    public PageContainer(List<T> elements, int currentPage, int pageSize, long totalCount) {
        this.elements = elements;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public int getFirstPage(){
        return 1;
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

    public long getTotalCount() {
        return totalCount;
    }

    public int getTotalPages(){
        return (int) Math.ceil((double) totalCount / pageSize);
    }

    public int getLastPage(){
        return getTotalPages();
    }
    public boolean hasNextPage(){
        return getCurrentPage() < getLastPage();
    }

    public boolean hasPrevPage(){
        return getCurrentPage() > 1;
    }
}
