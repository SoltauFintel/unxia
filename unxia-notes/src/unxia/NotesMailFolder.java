package unxia;

import java.util.List;

public class NotesMailFolder implements UnxiaMailFolder {

	public NotesMailFolder(String foldername) {
	}
	
	@Override
	public List<UnxiaMail> getEntries() {
		return getEntries(250);
	}

	@Override
	public List<UnxiaMail> getEntries(int max) {
		// TODO Auto-generated method stub
		return null;
	}
}
