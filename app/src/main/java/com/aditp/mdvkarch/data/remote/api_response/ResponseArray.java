package com.aditp.mdvkarch.data.remote.api_response;

import com.google.gson.annotations.SerializedName;

public class ResponseArray {
    public long id;
    public String name;
    public String full_name;
    public Owner owner;
    public String html_url;
    public String description;
    public String url;
    public String git_url;
    public String ssh_url;
    public String clone_url;
    public String svn_url;
    public String homepage;
    public int stargazers_count;
    public int watchers_count;
    public String language;
    public boolean has_issues;
    public boolean has_downloads;
    public boolean has_wiki;
    public boolean has_pages;
    public int forks_count;
    public int open_issues_count;
    public int forks;
    public int open_issues;
    public int watchers;
    public String default_branch;

    public ResponseArray() {
    }

    public ResponseArray(String name) {
        this.name = name;
    }
}