;; create database tables
				create table project_types(                                                     id int not null auto_increment,                                                 name   varchar(50) not null,                                                    primary key(id),                                                                unique(name)                                                                   )engine=InnoDB;
				
				insert into project_types values(0,'Resurfacing'),(0,'Markings'),(0,'Signs'),(0,'Reconstruction'),(0,'Investigation'),(0,'New'),(0,'Improve');
				
			   create table phase_ranks(                                                       id int not null auto_increment,                                                 name varchar(30) not null,                                                      primary key(id),                                                                unique(name)                                                                    )engine=InnoDB; 

				 insert into phase_ranks values (0,'Cancelled'),(0,'Unlikely'),(0,'Potential'),(0,'Planned'),(0,'Study'),(0,'Stage 1 Design'),(0,'Stage 2 Design'),(0,'Stage 3 Design'),(0,'ROW Acquisition'),(0,'Construction Ready'),(0,'Construction'),(0,'Punchlist'),(0,'Complete'),(0,'Post-Install Study');
				 				 
				 create table project_owners(                                                  	 id int not null auto_increment,                                                 name varchar(30) not null,                                                      primary key(id),                                                                unique(name)                                                                    )engine=InnoDB;
				 
        insert into project_owners values(0,'COB'),(0,'County'),(0,'State'),(0,'IU'),(0,'Transit'),(0,'Developer'),(0,'MCCSC');
				 
				create table funding_sources(                                                    id int not null auto_increment,                                                 name varchar(30) not null,                                                      primary key(id),                                                                unique(name)                                                                 )engine=InnoDB;
				
		    insert into funding_sources values(0,'HSIP'),(0,'TAP'),(0,'Operational');
				create table categories(                                                        id int not null auto_increment,                                                 name varchar(50) not null,                                                      primary key(id)                                                                )engine=InnoDB;		

				 insert into categories values(1,'Bike/Ped'),(2,'Signal'),(3,'Stop Control'),(4,'Roundabout'),(5,'Realignment'),(6,'Conversion');

				 create table sub_categories(                                                   id int not null auto_increment,                                                 category_id int,                                                                name varchar(50) not null,                                                      primary key(id),                                                                foreign key(category_id) references categories(id)                              )engine=InnoDB;
				insert into sub_categories values(1,1,'Sidewalk'),(2,1,'Sidepath'),(3,1,'Trail'),(4,1,'On-Street Bike'),(5,1,'Crosswalk'),(6,5,'Horizontal'),(7,5,'Vertical'),(8,6,'2 to 1 Way'),(9,6,'1 to 2 Way'),(10,6,'Lane Conversion');

				 create table sub_sub_categories(                                                 id int not null auto_increment,                                                 sub_id int,                                                                     name varchar(50) not null,                                                      primary key(id),                                                                foreign key(sub_id) references sub_categories(id)                              )engine=InnoDB;

				 insert into sub_sub_categories values(1,4,'Bike Lane'),(2,4,'Buffered Bke Lane'),(3,4,'Contra-Flow Bike Lane'),(4,4,'Climbing Bike Lane'),(5,4,'Advisory Bike Lane'),(6,4,'Sharrow'),(7,4,'Wayfinding'),(8,5,'Marked'),(9,5,'Marked and Signed'),(10,5,'RRFB'),(11,5,'HAWK');
				 
				 create table users(                                                             id int not null auto_increment,                                                 userid varchar(8) not null,                                                     fullname varchar(70) not null,                                                  role enum('View','Edit','Edit:Delete','Edit:Delete:Admin'),                     active char(1),                                                                 type enum('Plan','Eng','All'),                                                  primary key(id),                                                                unique(userid)                                                                  )engine=InnoDB;



 
