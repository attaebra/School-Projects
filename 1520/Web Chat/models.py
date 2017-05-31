from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class User(db.Model):
	user_id = db.Column(db.Integer, primary_key=True)
	username = db.Column(db.String(64), nullable=False)
	password = db.Column(db.String(64), nullable=False)
	
	def __init__(self, username, password):
		self.username = username
		self.password = password

	def __repr__(self):
		return '{}'.format(self.username)
	
class Message(db.Model):
	message_id = db.Column(db.Integer, primary_key=True)
	message_text = db.Column(db.String(128), nullable=False)
	username = db.Column(db.String(64), nullable=False)
	room_id = db.Column(db.Integer, db.ForeignKey('room.room_id'))
	
	def __init__(self, message_text, username, room_id):
		self.message_text = message_text
		self.username = username
		self.room_id = room_id
	
	def __repr__(self):
		return '{}'.format(self.message_text)
		
class Room(db.Model):
	room_id = db.Column(db.Integer, primary_key=True)
	room_name = db.Column(db.String(64), nullable=False)
	messages = db.relationship('Message', backref='room', lazy='dynamic')
	
	def __init__(self, room_name):
		self.room_name = room_name
	
	def __repr__(self):
		return '{}'.format(self.room_name)