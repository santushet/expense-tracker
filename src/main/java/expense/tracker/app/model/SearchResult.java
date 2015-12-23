package expense.tracker.app.model;

import java.util.List;

public class SearchResult<T> {

	private Long resultsCount;
	private List<T> result;
	public long getResultsCount() {
		return resultsCount;
	}
	public void setResultsCount(Long resultsCount) {
		this.resultsCount = resultsCount;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	public SearchResult(Long resultsCount, List<T> result) {
		super();
		this.resultsCount = resultsCount;
		this.result = result;
	}
	
}
