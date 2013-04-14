package models.question.submits;

import java.util.List;

import models.EMessages;
import models.management.Manager;

import com.avaje.ebean.Page;

/**
 * A page to show submissions
 * @author Ruben Taelman
 *
 */
public class SubmitsPage implements Page<Submit>{
    
    private int page = 0;
    private int resultsPerPage = Manager.DEFAULTPAGESIZE;
    
    private String filter;
    
    private List<Submit> list;
    
    /**
     * Create a new submissions page
     * @param page the current page
     * @param filter the string to filter on
     */
    public SubmitsPage(int page, String filter) {
        this.page = page;
        this.list = Submit.findAll(filter);
    }
    
    /**
     * The column headers for the list table
     * @return headers
     */
    public String[] getColumnHeaders() {
        return new String[]{EMessages.get("question.questions.submitslist.user"), EMessages.get("question.questions.submitslist.date"), ""};
    }

    @Override
    public String getDisplayXtoYofZ(String arg0, String arg1) {
        return (page * resultsPerPage + 1) + arg0 + toIndex() + arg1 + getTotalRowCount();
    }
    
    /**
     * The full list of submissions
     * @return list of all submissions
     */
    public List<Submit> getTotalList() {
        return list;
    }
    
    private int toIndex() {
        return Math.min(getTotalList().size(), (page + 1) * resultsPerPage);
    }

    @Override
    public List<Submit> getList() {
        return getTotalList().subList(page * resultsPerPage, toIndex());
    }

    @Override
    public int getPageIndex() {
        return page;
    }

    @Override
    public int getTotalPageCount() {
        return getTotalRowCount() / resultsPerPage;
    }

    @Override
    public int getTotalRowCount() {
        return getTotalList().size();
    }

    @Override
    public boolean hasNext() {
        return getTotalPageCount() > (page);
    }

    @Override
    public boolean hasPrev() {
        return page > 0;
    }

    @Override
    public Page<Submit> next() {
        return new SubmitsPage(page+1, filter);
    }

    @Override
    public Page<Submit> prev() {
        return new SubmitsPage(page-1, filter);
    }

}
