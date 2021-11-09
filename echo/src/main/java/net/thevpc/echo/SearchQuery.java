/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

/**
 *
 * @author thevpc
 */
public class SearchQuery {
    private String text;
    private boolean matchCase;
    private boolean wholeWord;
    private SearchQueryStrategy strategy;

    public SearchQuery(String text, boolean matchCase, boolean wholeWord, SearchQueryStrategy strategy) {
        this.text = text;
        this.matchCase = matchCase;
        this.wholeWord = wholeWord;
        this.strategy = strategy;
    }

    public String getText() {
        return text;
    }

    public boolean isMatchCase() {
        return matchCase;
    }

    public boolean isWholeWord() {
        return wholeWord;
    }

    public SearchQueryStrategy getStrategy() {
        return strategy;
    }

}
