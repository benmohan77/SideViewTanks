
// ballistic math
//http://en.wikipedia.org/wiki/Trajectory_of_a_projectile#Conditions_at_an_arbitrary_distance_x

// find y value for a given x value


//
// taking a shot:
// position of the shooting tank
// get the angle and initial velocity
// 
// plug this into the formula to get the y value of the projectile
// increase the x value by one
//
// x starts at the position of the tank (or +- 1?) - when does the tank destory itself
// keep increasing X
// calculate y for each new x
// keep increasing x and calculating y until you hit something
//

// the basic formula works
// if the shot angle is less than 90, step x from tank position positively
//   tankpos = 5; sequence should be 6, 7, 8, 9
// if the shot anle is greater than 90, step x from tank position negatively
// tank pos =5; sequence should be 4, 3, 2, 1

// if the tank is shooting to the left, angle should be greater than 90
// if the tank is shooting to the right, angle should be less than 90

public class Shot {
	public double power;
	public double angle;
}
