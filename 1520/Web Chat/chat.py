import time
import os
import datetime
import json
from flask import Flask, request, session, url_for, redirect, render_template, abort, flash
from models import db, User, Message, Room

app = Flask(__name__)

app.config.update(dict(
	SQLALCHEMY_TRACK_MODIFICATIONS = False,
	SECRET_KEY='development key',
	SQLALCHEMY_DATABASE_URI = 'sqlite:///' + os.path.join(app.root_path, 'chat.db')
))

app.config.from_envvar('CHAT', silent=True)
db.init_app(app)

@app.cli.command('initdb')
def initdb_command():
	"""Creates the database tables."""
	db.create_all()
	print('Initialized the database.')
	
@app.route('/')
def home_page():
	return render_template('login.html')

@app.route('/login', methods=['GET', 'POST'])
def login():
	error = None
	if request.method == 'POST':
		user_list = User.query.all()
		for user in user_list:
			if user.username == request.form['username'] and user.password == request.form['password']:
				session['logged_in'] = True
				session['user_id'] = user.user_id
				return redirect(url_for('room_list'))
		
		error = "Login failed!"
	else:
		if session.get('logged_in'):
			return redirect(url_for('room_list'))
				
	return render_template('login.html', error=error)

@app.route('/logout')
def logout():
	session.pop('logged_in', None)
	session.pop('user_id', None)
	session.pop('room_id', None)
	flash('You have successfully logged out')
	return redirect(url_for('login'))
	
@app.route('/create_account', methods=['POST'])
def create_account():
	new_user = User(request.form['username'], request.form['password'])
	db.session.add(new_user)
	db.session.commit()
	flash('Created new account!')
	return redirect(url_for('login'))
	
@app.route('/room_list', methods=['GET','POST'])
def room_list():
	if not 'logged_in' in session:
		abort(401)
	session['room_id'] = None
	rooms = Room.query.all()
	return render_template('rooms.html', chatrooms=rooms)
	
@app.route('/create_room', methods=['POST'])
def create_room():
	new_room = Room(request.form['roomname'])
	db.session.add(new_room)
	db.session.commit()
	flash('Created new chat room!')
	return redirect(url_for('room_list'))
	
@app.route('/room_<room_id>', methods=['GET','POST'])
def room(room_id):
	if not 'logged_in' in session:
		abort(401)
	room = Room.query.filter_by(room_id=room_id).first()
	session['room_id'] = room_id
	return render_template('chatroom.html', chatroom=room)
	
@app.route("/new_message", methods=["POST"])
def new_message():
	text = request.form["message"]
	room_id = session['room_id']
	user_id = session['user_id']
	room = Room.query.filter_by(room_id=room_id).first()
	user = User.query.filter_by(user_id=user_id).first()
	message = Message(text, user.username, room_id)
	room.messages.append(message)
	db.session.add(message)
	db.session.commit()
	list = {user.username : text}
	return json.dumps(list)

@app.route("/messages")
def get_messages():
	messages = Message.query.filter_by(room_id=session['room_id']).all()
	message_list = {}
	index = 0
	for m in messages:
		new_message = {m.username : m.message_text}
		message_list[index] = new_message
		index = index + 1
		
	return json.dumps(message_list)