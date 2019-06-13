package com.aditp.mdvkarch.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSearchRepositories{

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("incomplete_results")
	private boolean incompleteResults;

	@SerializedName("items")
	private List<ItemsItem> items;

    @SerializedName("message")
    private String message;

    @SerializedName("documentation_url")
    private String documentationUrl;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setIncompleteResults(boolean incompleteResults){
		this.incompleteResults = incompleteResults;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"ResponseSearchRepositories{" + 
			"total_count = '" + totalCount + '\'' + 
			",incomplete_results = '" + incompleteResults + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}