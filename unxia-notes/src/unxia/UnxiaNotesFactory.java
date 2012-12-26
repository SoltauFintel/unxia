package unxia;

/**
 * Unxia Factory für Lotus Notes 8.5
 */
public final class UnxiaNotesFactory implements UnxiaFactory {

	@Override
	public Unxia create() {
		return new Notes();
	}
}
