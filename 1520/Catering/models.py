from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class Staff(db.Model):
	staff_id = db.Column(db.Integer, primary_key=True)
	username = db.Column(db.String(32), nullable=False)
	password = db.Column(db.String(64), nullable=False)
	events = db.relationship('Event', secondary='events', primaryjoin='Staff.staff_id==events.c.staff_id', secondaryjoin='Event.event_id==events.c.event_id', backref=db.backref('staff', lazy='dynamic'), lazy='dynamic')
	
	def __init__(self, username, password):
		self.username = username
		self.password = password

	def __repr__(self):
		return 'Staff Name: {}'.format(self.username)
	
events = db.Table('events',
	db.Column('staff_id', db.Integer, db.ForeignKey('staff.staff_id')),
    db.Column('event_id', db.Integer, db.ForeignKey('event.event_id'))
)
	
class Event(db.Model):
	event_id = db.Column(db.Integer, primary_key=True)
	event_name = db.Column(db.String(64), nullable=False)
	event_date = db.Column(db.Date, nullable=False)
	customer_id = db.Column(db.Integer, db.ForeignKey('customer.customer_id'))
	
	def __init__(self, name, date, customer):
		self.event_name = name
		self.event_date = date
		self.customer_id = customer
		
	def __repr__(self):
		return '<Event Name: {}>'.format(self.event_name)
		
class Customer(db.Model):
	customer_id = db.Column(db.Integer, primary_key=True)
	username = db.Column(db.String(32), nullable=False)
	password = db.Column(db.String(64), nullable=False)
	
	def __init__(self, username, password):
		self.username = username
		self.password = password

	def __repr__(self):
		return '<Customer Name: {}>'.format(self.customer_name)
		