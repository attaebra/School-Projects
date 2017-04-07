function [p2] = apply_homography(p1, H)
    p1(3) = 1;
    p = p1';

    applied = H*p;

    x = applied(1)/applied(3);
    y = applied(2)/applied(3);

    p2 = [x y];
end