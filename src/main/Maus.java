package main;

import java.awt.*;

class Maus
{
	Point p;
	private boolean inF;

	public Maus(int x, int y)
	{
		p = new Point(x, y);
	}

	public void move()
	{
		p.translate(M.r.nextInt(11) - 5, M.r.nextInt(11) - 5);
		if(p.distance(M.wo) > M.ccr + 10)
			p.translate((p.x - M.wo.x) / -M.ccr, (p.y - M.wo.y) / -M.ccr);
		if(p.distance(M.wo) < M.ccr + 10)
			p.translate((p.x - M.wo.x) / M.ccr, (p.y - M.wo.y) / M.ccr);
		if(p.distance(M.wo) < M.ccr)
			inF = true;
		if(p.distance(M.wo) > M.ccr)
		{
			if(inF)
				M.anzahl += M.multi2;
			inF = false;
		}
	}
}