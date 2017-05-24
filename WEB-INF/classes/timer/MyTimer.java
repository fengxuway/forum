package timer;

import org.quartz.*;
import org.springframework.scheduling.quartz.*;
import org.springframework.stereotype.Component;

import entity.*;
import dao.*;
import service.*;
public  class MyTimer extends QuartzJobBean {
	ForbidListService forbidListService;
	protected void executeInternal(JobExecutionContext jbContent) throws JobExecutionException {
		forbidListService.removeTimeoutForbid();
	}
	public ForbidListService getForbidListService() {
		return forbidListService;
	}
	public void setForbidListService(ForbidListService forbidListService) {
		this.forbidListService = forbidListService;
	} 
}
