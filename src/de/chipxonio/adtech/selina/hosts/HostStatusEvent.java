package de.chipxonio.adtech.selina.hosts;

public class HostStatusEvent {
	private Host source;
	private int status;
	
	public HostStatusEvent(Host source, int status) {
		this.source = source;
		this.status = status;
	}

	public Host getSource() {
		return source;
	}

	public int getStatus() {
		return status;
	}
}
