package de.chipxonio.adtech.selrunner.engine;

public class SelRunnerEngine {
	private SelRunnerJob job;

	public void setJob(SelRunnerJob job) {
		this.job = job;
	}
	
	public SelRunnerJob getJob() {
		return this.job;
	}
	
	public void run() {
		this.job.run();
	}
}
