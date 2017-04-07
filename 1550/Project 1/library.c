#include <linux/fb.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <sys/ioctl.h>
#include <sys/select.h>
#include <termios.h>
#include <time.h>
#include "iso_font.h"
#include "library.h"

#define BMASK(c) (c & 0x001F) //Blue
#define GMASK(c) (c & 0x07E0) //Green
#define RMASK(c) (c & 0xF800) //Red

int fid;
color_t *address;
size_t buffer_size;
struct fb_var_screeninfo var_info;
struct fb_fix_screeninfo fixed_info;
struct termios old_term, new_term;
size_t buffer_size;

void init_graphics() {
	// Comment/uncomment line below to automatically clear when initializing
	clear_screen();
	fid = open("/dev/fb0", O_RDWR);
	ioctl(fid, FBIOGET_VSCREENINFO, &var_info);
	ioctl(fid, FBIOGET_FSCREENINFO, &fixed_info);
	buffer_size = var_info.yres_virtual * fixed_info.line_length;
	address = mmap(0, buffer_size, PROT_READ | PROT_WRITE, MAP_SHARED, fid, 0);
	ioctl(STDIN_FILENO, TCGETS, &old_term);
	new_term = old_term;
	new_term.c_lflag &= ~( ICANON | ECHO );
	ioctl(STDIN_FILENO, TCSETS, &new_term);
	//Turn blinking cursor off
	write(STDOUT_FILENO, "\033[?25l", 6);
}
void exit_graphics() {
	// Comment/Uncomment line below to automatically clear when exiting
	//clear_screen();
	//Reset terminal to old settings
	ioctl(STDIN_FILENO, TCSETS, &old_term);
	//Turn on cursor
	write(STDOUT_FILENO, "\033[?25h", 6);
	munmap(address, buffer_size);
	close(fid);
}
void clear_screen() {
	write(STDOUT_FILENO, "\033[2J", 6);
}
char getkey() {
	char key = '\0';
	struct timeval time = { .tv_sec = 0, .tv_usec = 0 };
	int in = STDIN_FILENO;
	fd_set fds;
	FD_SET(in,&fds);
	if (select(in+1, &fds, NULL, NULL, &time) > 0) {
		read(in, &key, 1);
	}
	return key;
}
void sleep_ms(long ms) {
	const struct timespec time = { .tv_sec = 0, .tv_nsec = ms*1000000 };
	nanosleep(&time, 0);
}
void draw_pixel(int x, int y, color_t color) {
	//Coordinate invalid
	if (y >= 480) return;
	int coordinate = (y * fixed_info.line_length / sizeof(color_t)) + x;
	*(address + coordinate) = color;
}

void draw_rect(int x1, int y1, int width, int height, color_t c) {
  int x2 = x1 + width;
  int y2 = y1 + height;
  int i;
  for (i = x1; i < x2; i++) {
    draw_pixel(i, y1, c);
  }
  for (i = y1; i < y2; i++) {
    draw_pixel(x1, i, c);
    draw_pixel(x2, i, c);
  }
  for (i = x1; i < x2; i++) {
    draw_pixel(i, y2, c);
  }
}

void draw_character(int x, int y, const char character, color_t c) {
	char row, row_val, column, pixel;
	for (row = 0; row < 16; row++) {
		row_val = iso_font[character * ISO_CHAR_HEIGHT + row];
		for (column = 0; column < 8; column++) {
			pixel = row_val>>column & 1;
			if (pixel) {
				draw_pixel(x+column, y+row, c);
			}
		}
	}
}
void draw_text(int x, int y, const char *text, color_t c) {
	while(*text != '\0') {
		draw_character(x,y,*text,c);
		if (x < fixed_info.line_length / sizeof(color_t)) {
			x += 8;
		} else {
			break;
		}
		text++;
	}
}
