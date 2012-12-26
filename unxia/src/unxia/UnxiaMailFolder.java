package unxia;

import java.util.List;

public interface UnxiaMailFolder {

	List<UnxiaMail> getEntries();

	List<UnxiaMail> getEntries(int max);
}
