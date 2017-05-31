import time
import os
import datetime
from flask import Flask, request, session, url_for, redirect, render_template, abort, flash
from models import db, Staff, Customer, Event

app = Flask(__name__)

#From flaskr example
app.config.update(dict(
	DEBUG=True,
	SECRET_KEY='development key',
	USERNAME='owner',
	PASSWORD='pass',
	SQLALCHEMY_DATABASE_URI = 'sqlite:///' + os.path.join(app.root_path, 'catering.db')
))

app.config.from_envvar('CATERING', silent=True)
db.init_app(app)

#from minitwit/flaskr
@app.cli.command('initdb')
def initdb_command():
	"""Creates the database tables."""
	db.create_all()
	print('Initialized the database.')

#redirects to proper url based on session	
@app.route('/')
def home_page():
	if session.get('loggedIn'):
			if session.get('isOwner'):
				return redirect(url_for('owner'))
			elif 'staffID' in session:
				return redirect(url_for('staff'))
			elif 'custID' in session:
				return redirect(url_for('customer'))
				
	return render_template('layout.html')

#either opt to make customer or login
@app.route('/login', methods=['GET', 'POST'])
def login():
	error = None
	if request.method == 'POST':
		if request.form['username'] == app.config['USERNAME'] and request.form['password'] == app.config['PASSWORD']:
			session['loggedIn'] = True
			session['isOwner'] = True
			return redirect(url_for('owner'))
		else:
			staff_list = Staff.query.all()
			customer_list = Customer.query.all()
			for staff in staff_list:
				if staff.username == request.form['username'] and staff.password == request.form['password']:
					session['loggedIn'] = True
					session['staffID'] = staff.staff_id
					return redirect(url_for('staff'))
			for customer in customer_list:
				if customer.username == request.form['username'] and customer.password == request.form['password']:
					session['loggedIn'] = True
					session['custID'] = customer.customer_id
					return redirect(url_for('customer'))
					
			error = "Login failed!"
	else:
		if session.get('loggedIn'):
			if session.get('isOwner'):
				return redirect(url_for('owner'))
			elif 'staffID' in session:
				return redirect(url_for('staff'))
			elif 'custID' in session:
				return redirect(url_for('customer'))
				
	return render_template('login.html', error=error)

#logs out
@app.route('/logout')
def logout():
	session.pop('loggedIn', None)
	session.pop('staffID', None)
	session.pop('custID', None)
	session['isOwner'] = False
	flash('You have successfully logged out')
	return redirect(url_for('login'))
	
@app.route('/create_customer', methods=['POST'])
def create_customer():
	new_customer = Customer(request.form['cust_username'], request.form['cust_password'])
	db.session.add(new_customer)
	db.session.commit()
	flash('Created new customer account!')
	return redirect(url_for('login'))
	
@app.route('/create_staff', methods=['POST'])
def create_user():
	new_staff = Staff(request.form['username'], request.form['password'])
	db.session.add(new_staff)
	db.session.commit()
	flash('Ceated a new staff member account!')
	return redirect(url_for('owner'))
	
@app.route('/owner')
def owner():
	if not session.get('isOwner'):
		abort(401)
	elif not 'loggedIn' in session:
		abort(401)
	events = Event.query.all()
	return render_template('owner.html', events=events)

@app.route('/customer')
def customer():
	if not 'loggedIn' in session:
		abort(401)
	if not 'custID' in session:
		abort(401)
	id = session['custID']
	customer = Customer.query.filter_by(customer_id=id).first()
	events = Event.query.filter_by(customer_id=id).all()
	return render_template('customer.html', events=events, customer_name=customer.username)

@app.route('/staff')
def staff():
	if not 'loggedIn' in session:
		abort(401)
	if not 'staffID' in session:
		abort(401)
	id = session['staffID']
	staff = Staff.query.filter_by(staff_id=id).first()
	scheduled_events = staff.events
	event_ids = []
	for e in scheduled_events:
		event_ids.append(e.event_id)
	events = Event.query.filter(~Event.event_id.in_(event_ids)).all()
	for e in events:
		if e.staff.count() == 3:
			events.remove(e)
	return render_template('staff.html', events=events, scheduled_events=scheduled_events, username=staff.username)	
	
@app.route('/create_event', methods=['POST'])
def create_event():
	if not 'loggedIn' in session:
		abort(401)
	if not 'custID' in session:
		abort(401)
	id = session['custID']
	event_date = datetime.datetime.strptime(request.form['event_date'], "%Y-%m-%d").date()
	new_event = Event(request.form['event_name'], event_date, id)
	all_events = Event.query.all()
	for e in all_events:
		if e.event_date == event_date:
			flash('There is already an event scheduled on that day!')
			return redirect(url_for('customer'))
		
	db.session.add(new_event)
	db.session.commit()
	flash('Scheduled a new event!')
	return redirect(url_for('customer'))
#@app.route('/')
#def redirect():
	#return redirect(url_for('home'))
	
@app.route('/delete_event_<event_id>')
def delete_event(event_id):
	if not 'loggedIn' in session:
		abort(401)
	if not 'custID' in session:
		abort(401)
	Event.query.filter_by(event_id=event_id).delete()
	db.session.commit()
	flash('Deleted event from schedule!')
	return redirect(url_for('customer'))
	
@app.route('/add_staff_<event_id>')
def add_staff(event_id):
	if not 'loggedIn' in session:
		abort(401)
	if not 'staffID' in session:
		abort(401)
	id = session['staffID']
	event = Event.query.filter_by(event_id=event_id).first()
	staff = Staff.query.filter_by(staff_id=id).first()
	staff.events.append(event)
	db.session.commit()
	flash('Added event to work schedule!')
	return redirect(url_for('staff'))

#@app.route('/register', methods=['GET', 'POST'])
#def register():
	#"""Registers the user."""
	#if g.user:
		#return redirect(url_for('register'))
	#error = None
	#if request.method == 'POST':
		#if not request.form['username']:
			#error = 'You have to enter a username'
		#elif not request.form['password']:
			#error = 'You have to enter a password'
		#elif request.form['password'] != request.form['password2']:
			#error = 'The two passwords do not match'
		#elif get_customer_id(request.form['username']) is not None:
		#	error = 'The username is already taken'
		#else:
		#	db.session.add(Customer(request.form['username'], request.form['password']))
		#	db.session.commit()
		#	flash('You were successfully registered and can login now')
		#	return redirect(url_for('login'))
	#return render_template('register.html', error=error)