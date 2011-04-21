package de.chipxonio.adtech.selina.engine;

public class HostQueueEvent {
	private HostQueue source;
	private int status;
	
	public static int RUNNING = 1;
	public static int STOPPED = 0;
	public static int ACTIVE = 2;
	public static int INACTIVE = 0;
	
	public HostQueueEvent(HostQueue source, int status) {
		this.source = source;
		this.status = status;
	}

	public HostQueue getSource() {
		return source;
	}

	public int getStatus() {
		return status;
	}
}
