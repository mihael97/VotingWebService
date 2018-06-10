package hr.fer.zemris.java.dao.sql;

import java.util.ArrayList;
import java.util.List;

public abstract class SQLData {
	public static List<String> POLLS;
	public static List<String> POLLSOPTIONS_BANDS;
	public static List<String> POLLSOPTIONS_CARS;

	static {
		POLLS = new ArrayList<>();
		POLLS.add(
				"\"Voting for favourite band:\",\"From given bands,which one is your favoutite. Please click on link for voting\"");
		POLLS.add("\"Voting for best car manufacturer:\",\"From given manufactures,which one is your favourite?\"");

		POLLSOPTIONS_BANDS.add("\"The Beatles\",\"https://www.youtube.com/watch?v=z9ypq6_5bsg\",?,0");
		POLLSOPTIONS_BANDS.add("\"The Pletters\",\"https://www.youtube.com/watch?v=H2di83WAOhU\",?,0");
		POLLSOPTIONS_BANDS.add("\"The Beach Boys\",\"https://www.youtube.com/watch?v=2s4slliAtQU\",?,0");
		POLLSOPTIONS_BANDS.add("\"The Four Seasons\",\"https://www.youtube.com/watch?v=y8yvnqHmFds\",?,0");
		POLLSOPTIONS_BANDS.add("\"The Marcles\",\"https://www.youtube.com/watch?v=qoi3TH59ZEs\",?,0");
		POLLSOPTIONS_BANDS.add("\"The Everly Brothers\",\"https://www.youtube.com/watch?v=tbU3zdAgiX8\",?,0");
		POLLSOPTIONS_BANDS.add("\"The Mamas And The Papas\",\"https://www.youtube.com/watch?v=N-aK6JnyFmk\",?,0");
		
		POLLSOPTIONS_CARS.add("\"Subaru\",\"https://www.youtube.com/watch?v=6yWFacB_ayo\",?,0");
		POLLSOPTIONS_CARS.add("\"Fiat\",\"https://www.youtube.com/watch?v=WmpTqJ5i07U\",?,0");
		POLLSOPTIONS_CARS.add("\"VW\",\"https://www.youtube.com/watch?v=-UQ4SyKuVR4\",2,0})");
	}
}
