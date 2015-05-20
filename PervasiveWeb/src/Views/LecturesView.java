package Views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parse4j.ParseCloud;
import org.parse4j.ParseException;
import org.parse4j.callback.FunctionCallback;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import domainEntities.Lecture;
import domainEntities.Professor;

public class LecturesView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2570793839851203621L;
	
	private HorizontalLayout tableSpace = new HorizontalLayout();
	private VerticalLayout prevLayout = new VerticalLayout();
	private VerticalLayout nowLayout = new VerticalLayout();
	private VerticalLayout nextLayout = new VerticalLayout();
	
	private Label title = new Label("Today's lectures");
	private Label prevLabel = new Label("Previous lectures");
	private Label nowLabel = new Label("Ongoing lectures");
	private Label nextLabel = new Label("Next lectures");
	
	private Table prevTable = new Table();
	private Table nowTable = new Table();
	private Table nextTable = new Table();
	private HashMap<String,Integer> dayMap = new HashMap<String,Integer>();
	
	
	public LecturesView()
		{
			dayMap.put("Monday", 1);
			dayMap.put("Tuesday", 2);
			dayMap.put("Wednesday", 3);
			dayMap.put("Thursday", 4);
			dayMap.put("Friday", 5);
			dayMap.put("Saturday", 6);
			dayMap.put("Sunday", 7);
			getWeeklySchedule();
			
			title.addStyleName(ValoTheme.LABEL_BOLD);
			title.addStyleName(ValoTheme.LABEL_LARGE);
			title.setSizeUndefined();
			title.addStyleName("animated");
			title.addStyleName("flipInX");
			title.addStyleName("delay05");
			prevLabel.setSizeUndefined();
			prevLabel.addStyleName(ValoTheme.LABEL_BOLD);
			nowLabel.setSizeUndefined();
			nowLabel.addStyleName("animated");
			nowLabel.addStyleName("tada");
			nowLabel.addStyleName("delay09");
			nowLabel.addStyleName(ValoTheme.LABEL_BOLD);
			nextLabel.setSizeUndefined();
			nextLabel.addStyleName("animated");
			nextLabel.addStyleName("tada");
			nextLabel.addStyleName("delay1");
			nextLabel.addStyleName(ValoTheme.LABEL_BOLD);
			prevTable.setImmediate(true);
			nowTable.setImmediate(true);
			nextTable.setImmediate(true);
			prevLabel.setVisible(false);
			nowLabel.setVisible(false);
			nextLabel.setVisible(false);
			prevTable.setVisible(false);
			nowTable.setVisible(false);
			nextTable.setVisible(false);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					prevLabel.setVisible(true);
					prevLabel.addStyleName("animated");
					prevLabel.addStyleName("tada");
					//prevLabel.addStyleName("delay08");
					prevLabel.markAsDirty();
					UI.getCurrent().push();
				}}, 1000);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					prevTable.setVisible(true);
					prevTable.addStyleName("animated");
		            prevTable.addStyleName("zoomInUp");
		            prevTable.markAsDirty();
		            UI.getCurrent().push();
				}}, 1500);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					nowLabel.setVisible(true);
					nowLabel.addStyleName("animated");
					nowLabel.addStyleName("tada");
					nowLabel.markAsDirty();
					UI.getCurrent().push();
					
				}}, 2000);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					nowTable.setVisible(true);
					nowTable.addStyleName("animated");
					nowTable.addStyleName("zoomInUp");
					nowTable.markAsDirty();
		            UI.getCurrent().push();
					
				}}, 2500);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					nextLabel.setVisible(true);
					nextLabel.addStyleName("animated");
					nextLabel.addStyleName("tada");
					nextLabel.markAsDirty();
					UI.getCurrent().push();
					
				}}, 3000);
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					nextTable.setVisible(true);
					nextTable.addStyleName("animated");
					nextTable.addStyleName("zoomInUp");
					nextTable.markAsDirty();
		            UI.getCurrent().push();
					
				}}, 3500);
			
			prevLayout.addComponents(prevLabel,prevTable);
			prevLayout.setComponentAlignment(prevLabel, Alignment.TOP_CENTER);
			prevLayout.setComponentAlignment(prevTable, Alignment.TOP_CENTER);
			nowLayout.addComponents(nowLabel,nowTable);
			nowLayout.setComponentAlignment(nowLabel, Alignment.TOP_CENTER);
			nowLayout.setComponentAlignment(nowTable, Alignment.TOP_CENTER);
			nextLayout.addComponents(nextLabel,nextTable);
			nextLayout.setComponentAlignment(nextLabel, Alignment.TOP_CENTER);
			nextLayout.setComponentAlignment(nextTable, Alignment.TOP_CENTER);
			
			//setPrevTable();
			//setNowTable();
			setNextTable();
			
			tableSpace.setWidth("100%");
			tableSpace.setSpacing(true);
			tableSpace.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
			tableSpace.addComponents(prevLayout,nowLayout,nextLayout);
			tableSpace.setComponentAlignment(prevLayout, Alignment.TOP_CENTER);
			tableSpace.setComponentAlignment(nowLayout, Alignment.TOP_CENTER);
			tableSpace.setComponentAlignment(nextLayout, Alignment.TOP_CENTER);
			
			this.setWidth("100%");
			this.addComponents(title,tableSpace);
			this.setSpacing(true);
			this.setComponentAlignment(title, Alignment.TOP_CENTER);
			this.setComponentAlignment(tableSpace, Alignment.TOP_CENTER);
		}

	private void setNextTable() {
		LinkedList<Lecture> list = new LinkedList<Lecture>();
		Professor cool = new Professor("cool prof","course_rnd","1","1","1","1");
		Lecture lec1= new Lecture(cool.getName(), "cool stuff", "cool things", "8:30","10:00");
		list.add(lec1);
		list.add(new Lecture(cool.getName(),"Crazy Stuff","too many things","10:15","11:45"));
		list.add(new Lecture(cool.getName(),"Useless Stuff","random things","10:15","11:45"));
		list.add(new Lecture(cool.getName(),"STFU","many things","12:00","13:30"));
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,list);
		nextTable.setContainerDataSource(container);
		nextTable.setHeight("167px");
		if(list.size()<=5)
			{
			nextTable.setPageLength(list.size());
			}else nextTable.setPageLength(5);
		nextTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nextTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		nextTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		nextTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -6373787444551133713L;

			@Override
			public void itemClick(ItemClickEvent event) {
				BeanItem<?> selected =(BeanItem<?>) event.getItem();
				Lecture lecture = (Lecture) selected.getBean();
				//System.out.println(lecture.getTopics());
				//System.out.println(event.getItemId());
				Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getProf()+" which talks about "+lecture.getTopics(), Notification.Type.TRAY_NOTIFICATION);
			}
		});
		
	}

	private void setNowTable() {
		LinkedList<Lecture> list = new LinkedList<Lecture>();
		//list.add(new Lecture("Cool prof","Important Stuff","many things","8:30","10:00"));
		//list.add(new Lecture("Not Cool prof","Crazy Stuff","too many things","10:15","11:45"));
		//list.add(new Lecture("Boring prof","Useless Stuff","random things","10:15","11:45"));
		//list.add(new Lecture("Angry profe","STFU","many things","12:00","13:30"));
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,list);
		nowTable.setContainerDataSource(container);
		nowTable.setHeight("167px");
		if(list.size()<=5)
		{
			nowTable.setPageLength(list.size());
		}else nowTable.setPageLength(5);
		nowTable.addStyleName(ValoTheme.TABLE_COMPACT);
		nowTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		nowTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		nowTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -6373787444551133713L;

			@Override
			public void itemClick(ItemClickEvent event) {
				BeanItem<?> selected =(BeanItem<?>) event.getItem();
				Lecture lecture = (Lecture) selected.getBean();
				//System.out.println(lecture.getTopics());
				//System.out.println(event.getItemId());
				Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getProf()+" which talks about "+lecture.getTopics(), Notification.Type.WARNING_MESSAGE);
			}
		});
		
	}

	private void setPrevTable() {
		LinkedList<Lecture> list = new LinkedList<Lecture>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,0);
		cal.set(Calendar.MONTH,0);
		cal.set(Calendar.YEAR, 0);
		cal.set(Calendar.SECOND, 0);
	
		
		//list.add(new Lecture("Cool prof","Important Stuff","many things","8:30","10:00"));
		//list.add(new Lecture("Not Cool prof","Crazy Stuff","too many things","10:15","11:45"));
		//list.add(new Lecture("Boring prof","Useless Stuff","random things","10:15","11:45"));
		//list.add(new Lecture("Angry profe","STFU","many things","12:00","13:30"));
		BeanItemContainer<Lecture> container = new BeanItemContainer<Lecture>(Lecture.class,list);
		prevTable.setContainerDataSource(container);
		prevTable.setHeight("167px");
		if(list.size()<=5)
		{
			prevTable.setPageLength(list.size());
		}else prevTable.setPageLength(5);
		prevTable.addStyleName(ValoTheme.TABLE_COMPACT);
		prevTable.addStyleName(ValoTheme.TABLE_SMALL);
		prevTable.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		prevTable.setColumnAlignments(Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER,Align.CENTER);
		prevTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 8643717639430433327L;

			@Override
			public void itemClick(ItemClickEvent event) {
				BeanItem<?> selected =(BeanItem<?>) event.getItem();
				Lecture lecture = (Lecture) selected.getBean();
				//System.out.println(lecture.getTopics());
				//System.out.println(event.getItemId());
				Notification.show("Hey ya!", "You selected lesson of professor "+lecture.getProf()+" which talks about "+lecture.getTopics(), Notification.Type.HUMANIZED_MESSAGE);
			}
		});
	}
	
	private void getWeeklySchedule()
		{
		LinkedList<Lecture> pastList = new LinkedList<Lecture>();
		LinkedList<Lecture> nowList = new LinkedList<Lecture>();
		LinkedList<Lecture> nextList = new LinkedList<Lecture>();
		final Calendar currentCal =Calendar.getInstance();
		final Date now =currentCal.getTime();
		currentCal.setTime(now);
		SimpleDateFormat dayName = new SimpleDateFormat("EEEE",Locale.ENGLISH);
		final SimpleDateFormat nowName = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
		final String hourTime = nowName.format(now);
		final String dayTime = dayName.format(now);
		ParseCloud.callFunctionInBackground("getWeeklySchedule", null, new FunctionCallback<JSONArray>(){

			@Override
			public void done(JSONArray result, ParseException parseException) {
				for(int i=0;i<result.length();i++)
					{
					JSONObject jsn = result.getJSONObject(i);
					String professor= jsn.getString("Professor");
					String start = jsn.getString("Start_Time");
					String end = jsn.getString("End_Time");
					String day = jsn.getString("Day");
					String lesson = jsn.getString("Lesson");
					try {
						Date date =  nowName.parse(start);
						Calendar tempCal = Calendar.getInstance();
						tempCal.setTime(date);
						tempCal.set(Calendar.DAY_OF_MONTH, currentCal.get(Calendar.DAY_OF_MONTH));
						tempCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH));
						tempCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
						date=tempCal.getTime();
						System.out.println("PORCADDIOOOOOOOOOOOO    "+date.toString()+"             "+now);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(professor+" "+lesson+" "+start+" "+end+" "+day+" day+time= "+dayTime+" "+hourTime+" compareToStart "+start.compareTo(hourTime)+" compareToEnd "+end.compareTo(hourTime));
					}
				System.out.println(dayTime+" "+hourTime);
			}});
		}

}
