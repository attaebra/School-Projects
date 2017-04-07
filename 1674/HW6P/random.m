jupiter = imread('jupiter.jpg');
egg = imread('egg.jpg');

the_edges_jup = detectEdges(jupiter, 150);
the_edges_egg = detectEdges(egg, 10);

[size_x, size_y, dim] = size(egg);

im2 = zeros(size_x, size_y);
for i = 1:size(the_edges_egg, 1)
   im2(the_edges_egg(i,2),the_edges_egg(i,1)) = the_edges_egg(i,3);
end
imshow(egg);
impixelinfo;

circles = detectCircles(egg, the_edges_egg, 10, 20);
