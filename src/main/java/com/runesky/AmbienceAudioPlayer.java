package com.runesky;

public class AmbienceAudioPlayer
{
	private long pausedMilliseconds = 0;
	private long startedAtMilliseconds = 0;
	private boolean playing = false;

	public boolean load(String audioFilePath)
	{
		return true;
	}

	public boolean resume(String audioFilePath, boolean muted)
	{
		if (!playing)
		{
			startedAtMilliseconds = System.currentTimeMillis() - pausedMilliseconds;
			playing = true;
		}

		return true;
	}

	public void pause()
	{
		if (playing)
		{
			pausedMilliseconds = getPlaybackElapsedMs();
			playing = false;
		}
	}

	public void reset()
	{
		pausedMilliseconds = 0;
		startedAtMilliseconds = System.currentTimeMillis();
	}

	public void seekByMs(long deltaMs)
	{
		seekToMs(getPlaybackElapsedMs() + deltaMs);
	}

	public void seekToMs(long targetMs)
	{
		pausedMilliseconds = Math.max(0, targetMs);

		if (playing)
		{
			startedAtMilliseconds = System.currentTimeMillis() - pausedMilliseconds;
		}
	}

	public void setMuted(boolean muted)
	{
		// No audio playback in the Plugin Hub-safe version.
	}

	public long getPlaybackElapsedMs()
	{
		if (playing)
		{
			return Math.max(0, System.currentTimeMillis() - startedAtMilliseconds);
		}

		return pausedMilliseconds;
	}

	public long getPlaybackLengthMs()
	{
		return 0L;
	}

	public boolean isPlaying()
	{
		return playing;
	}

	public void close()
	{
		playing = false;
		pausedMilliseconds = 0;
		startedAtMilliseconds = 0;
	}
}
