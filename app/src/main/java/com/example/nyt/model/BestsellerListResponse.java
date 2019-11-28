package com.example.nyt.model;

public class BestsellerListResponse {
    private String status;
    private String copyright;
    private String section;
    private String last_updated;
    private int num_results;


    // Be aware that the results field in this case is an object that represents the bestseller list.
    // This should be represented as another Java class, and the actual list of Book would be
    // contained in this other class.
    private BestsellerList results;

    public String getStatus() {
        return status;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getSection() {
        return section;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public int getNum_results() {
        return num_results;
    }

    public BestsellerList getResults() {
        return results;
    }
}
