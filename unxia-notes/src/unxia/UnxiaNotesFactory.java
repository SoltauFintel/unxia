package unxia;

/**
 * Unxia Factory f�r Lotus Notes 8.5
 */
public final class UnxiaNotesFactory implements UnxiaFactory {

	@Override
	public Unxia create() {
		return new Notes();
	}
}
