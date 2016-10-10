import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

public class Sound {
	
	public Sound(){
		
	}
	
	public void setChord(){
		
	}
	
	public void playNote(){
		/** The MIDI channel to use for playing the note. */
		int nChannelNumber = 0;
		int	nNoteNumber = 0;	// MIDI key number
		int	nVelocity = 0;

		/*
		 *	Time between note on and note off event in
		 *	milliseconds. Note that on most systems, the
		 *	best resolution you can expect are 10 ms.
		 */
		int	nDuration = 0;
		int nNoteNumberArgIndex = 0;

		nNoteNumber = 0;
		nNoteNumber = Math.min(127, Math.max(0, nNoteNumber));
		nVelocity = 127;
		nVelocity = Math.min(127, Math.max(0, nVelocity));
		nDuration = 1000;
		nDuration = Math.max(0, nDuration);



		/*
		 *	We need a synthesizer to play the note on.
		 *	Here, we simply request the default
		 *	synthesizer.
		 */
		Synthesizer	synth = null;
		try
		{
			synth = MidiSystem.getSynthesizer();
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		/*
		 *	Of course, we have to open the synthesizer to
		 *	produce any sound for us.
		 */
		try
		{
			synth.open();
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		/*
		 *	Turn the note on on MIDI channel 1.
		 *	(Index zero means MIDI channel 1)
		 */
		MidiChannel[]	channels = synth.getChannels();
		MidiChannel	channel = channels[nChannelNumber];
		channel.noteOn(nNoteNumber, nVelocity);

		/*
		 *	Wait for the specified amount of time
		 *	(the duration of the note).
		 */
		try
		{
			Thread.sleep(nDuration);
		}
		catch (InterruptedException e)
		{
		}

		/*
		 *	Turn the note off.
		 */
		channel.noteOff(nNoteNumber);

		/* Close the synthesizer.
		 */
		synth.close();
	}
}
