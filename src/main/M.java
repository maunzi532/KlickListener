package main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

class M
{
	private static JFrame fr;
	private static JPanel cpl;
	private static Graphics2D cgd;
	private static JButton mb1;
	private static JButton mb2;
	private static JButton mb3;
	private static JLabel ml1;
	private static JLabel ml2;
	private static JLabel ml3;
	private static JPanel gpl;
	private static JPanel gpr;
	private static Graphics2D ggd;
	private static Image imgg;
	private static Image cimg;

	static double anzahl = 0;
	private static double multiplikator = 1;
	static double multi2 = 1;
	private static double m1kosten;
	private static double m2kosten;
	private static double m3kosten;
	private static long gek1 = 1;
	private static long gek2 = 1;
	private static long gek3;
	static Point wo;
	private static int clicked = 0;
	private static long time;
	static Random r;
	private static ArrayList<Maus> maus;
	private static HashMap<String, Image> imgs;
	private static int clw = 200;
	private static final int fh = 600;
	private static int maxccr = 30;
	static int ccr = maxccr;
	private static double yellow;
	private static int yellow2;
	private static final int yellow2length = 1000;
	private static String fi = "speicher.txt";
	private static double vorher;
	private static double lps;

	public static void main(String[] args) throws IOException
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		Lader7.inJarCheck();
		load();

		fr = new JFrame();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setResizable(false);

		JPanel pl = new JPanel(new BorderLayout());
		fr.setContentPane(pl);

		JPanel vpl = new JPanel(new BorderLayout());
		pl.add(vpl, BorderLayout.CENTER);

		cpl = new JPanel();
		cpl.setPreferredSize(new Dimension(clw, clw));
		gpl = new JPanel();
		gpl.setPreferredSize(new Dimension(clw / 4, clw));
		gpr = new JPanel();
		gpr.setPreferredSize(new Dimension(clw / 4, clw));
		vpl.add(cpl, BorderLayout.CENTER);
		vpl.add(gpl, BorderLayout.LINE_START);
		vpl.add(gpr, BorderLayout.LINE_END);

		JPanel txpl = new JPanel();
		txpl.setLayout(new BoxLayout(txpl, BoxLayout.PAGE_AXIS));
		txpl.setBackground(Color.BLACK);
		pl.add(txpl, BorderLayout.PAGE_END);

		mb1 = new JButton("W");
		mb2 = new JButton("W");
		mb3 = new JButton("W");

		ml1 = new JLabel("W");
		ml2 = new JLabel("W");
		ml3 = new JLabel("W");

		txpl.add(ml1);
		ml1.setAlignmentX(Component.CENTER_ALIGNMENT);
		txpl.add(mb1);
		mb1.setAlignmentX(Component.CENTER_ALIGNMENT);
		txpl.add(ml2);
		ml2.setAlignmentX(Component.CENTER_ALIGNMENT);
		txpl.add(mb2);
		mb2.setAlignmentX(Component.CENTER_ALIGNMENT);
		txpl.add(ml3);
		ml3.setAlignmentX(Component.CENTER_ALIGNMENT);
		txpl.add(mb3);
		mb3.setAlignmentX(Component.CENTER_ALIGNMENT);

		fr.pack();

		cimg = cpl.createImage(clw, clw);
		cgd = (Graphics2D) cimg.getGraphics();
		imgg = gpl.createImage(clw / 4, clw);
		ggd = (Graphics2D) imgg.getGraphics();

		fr.setTitle("Klicks: " + (long) anzahl);

		wo = new Point(clw / 2, clw / 2);

		maus = new ArrayList<>();

		r = new Random();

		ml1.setForeground(Color.white);
		ml2.setForeground(Color.white);
		ml3.setForeground(Color.white);

		multiplikator = Math.pow(1.6, gek1 - 1);
		m1kosten = Math.pow(2.0, gek1 - 1) * 1;

		mb1.addActionListener(e ->
		{
			if(anzahl >= m1kosten)
			{
				anzahl -= m1kosten;
				multiplikator = Math.pow(1.6, gek1);
				m1kosten = Math.pow(2.0, gek1) * 1;
				gek1++;
			}
		});

		multi2 = Math.pow(1.4, gek2 - 1);
		m2kosten = Math.pow(2.5, gek2 - 1) * 1;

		mb2.addActionListener(e ->
		{
			if(anzahl >= m2kosten)
			{
				anzahl -= m2kosten;
				multi2 = Math.pow(1.4, gek2);
				m2kosten = Math.pow(2.5, gek2) * 1;
				gek2++;
			}
		});

		for(int i = 0; i < gek3; i++)
			maus.add(new Maus(clw / 2, clw / 2));
		m3kosten = Math.pow(1.1, gek3 - 1) * 5;

		mb3.addActionListener(e ->
		{
			if(anzahl >= m3kosten)
			{
				anzahl -= m3kosten;
				maus.add(new Maus(clw / 2, clw / 2));
				m3kosten = Math.pow(1.1, gek3) * 5;
				gek3++;
			}
		});

		pl.setBackground(Color.black);
		cpl.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getPoint().distance(wo.x, wo.y) <= ccr)
				{
					if(yellow2 > 0)
					{
						anzahl += multiplikator * Math.pow(maxccr / ccr, 3);
						ccr--;
						clicked += 10;
						if(ccr <= 0)
						{
							yellow2 = 0;
							ccr = maxccr;
						}
					}
					else
					{
						anzahl += multiplikator;
						clicked += 10;
					}
				}
			}
		});

		fr.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent arg0)
			{
				StringBuilder save = new StringBuilder();
				save.append(anzahl);
				save.append('\n');
				save.append(gek1);
				save.append('\n');
				save.append(gek2);
				save.append('\n');
				save.append(gek3);
				save.append('\n');
				Lader7.textStore(fi, save.toString());
			}
		});
		readImages2();
		time = System.currentTimeMillis();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		fr.setLocation(screen.width / 2 - clw * 3 / 4, screen.height / 2 - fh / 2);
		fr.setVisible(true);
		//noinspection InfiniteLoopStatement
		while(true)
		{
			drawandmove();
			if(System.currentTimeMillis() > time + 1000)
			{
				time = System.currentTimeMillis();
				sek();
			}
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException ignored){}
		}
	}

	private static void readImages2()
	{
		imgs = new HashMap<>();
		imgs.put("Lampe", Lader7.imageResource("Lampe.png"));
		imgs.put("Gelbe_Lampe", Lader7.imageResource("Gelbe_Lampe.png"));
		imgs.put("Maus", Lader7.imageResource("Maus.png"));

	}

	private static void sek()
	{
		lps = anzahl - vorher;
		vorher = anzahl;
	}

	private static void drawandmove()
	{
		mb1.setText("Mehr Licht... Kosten: " + d2s(m1kosten, 6));
		mb2.setText("Cursorkraft... Kosten: " + d2s(m2kosten, 6));
		mb3.setText("Mehr Cursor... Kosten: " + d2s(m3kosten, 6));
		ml1.setText("Lichtkraft: " + d2s(multiplikator, 6) + " (Stufe " + gek1 + ")");
		ml2.setText("Cursorkraft: " + d2s(multi2, 6) + " (Stufe " + gek2 + ")");
		ml3.setText("Hilfscursors: " + maus.size() + " (Stufe " + gek3 + ")");
		cgd.setColor(Color.black);
		cgd.fillRect(0, 0, cimg.getWidth(null), cimg.getHeight(null));
		if(yellow >= 1)
		{
			yellow = 0;
			yellow2 = yellow2length;
		}
		if(yellow2 > 0)
		{
			yellow = 0;
			yellow2--;
			cgd.drawImage(imgs.get("Gelbe_Lampe"), wo.x - ccr, wo.y - ccr, ccr * 2, ccr * 2, null);
			for(int i = 0; i * 2 < clicked; i++)
				cgd.drawImage(imgs.get("Gelbe_Lampe"), wo.x - ccr, wo.y - ccr, ccr * 2, ccr * 2, null);
		}
		else
		{
			cgd.drawImage(imgs.get("Lampe"), wo.x - ccr, wo.y - ccr, ccr * 2, ccr * 2, null);
			for(int i = 0; i * 5 < clicked; i++)
				cgd.drawImage(imgs.get("Lampe"), wo.x - ccr, wo.y - ccr, ccr * 2, ccr * 2, null);
		}
		if(yellow2 <= 0)
			ccr = maxccr;
		if(clicked > 0)
			clicked -= 1;
		fr.setTitle("Licht: " + d2s(anzahl, 6) + " LPS: " + d2s(lps, 6));
		if(r.nextInt(2) == 0)
			wo.translate(r.nextInt(7) - 3, r.nextInt(7) - 3);
		Point vorwo = new Point(wo);
		if(wo.x < ccr)
			wo.x = ccr;
		if(wo.x > clw - ccr)
			wo.x = clw - ccr;
		if(wo.y < ccr)
			wo.y = ccr;
		if(wo.y > clw - ccr)
			wo.y = clw - ccr;
		for(int i = 0; i < maus.size(); i++)
		{
			maus.get(i).move();
			cgd.drawImage(imgs.get("Maus"), maus.get(i).p.x, maus.get(i).p.y, 10, 10, null);
		}
		if(!vorwo.equals(wo))
		{
			cgd.setColor(Color.yellow);
			cgd.drawRect(0, 0, clw - 1, clw - 1);
			if(yellow2 > 0)
				yellow2 += r.nextInt(50);
			else
				yellow += r.nextDouble() / 20;
		}
		ggd.setColor(new Color(1000500));
		ggd.fillRect(0, 0, clw / 4, clw);
		ggd.setColor(Color.yellow);
		if(yellow2 > 0)
			ggd.fillRect(0, clw - (int) ((double) yellow2 / yellow2length * clw), clw / 4,
					(int) ((double) yellow2 / yellow2length * clw));
		else
			ggd.fillRect(0, clw - (int) (yellow * clw), clw / 4, (int) (yellow * clw));
		cpl.getGraphics().drawImage(cimg, 0, 0, null);
		gpl.getGraphics().drawImage(imgg, 0, 0, null);
		gpr.getGraphics().drawImage(imgg, 0, 0, null);
	}

	private static void load() throws IOException
	{
		String c1 = Lader7.textSavedata(fi);
		if(c1 == null || c1.length() <= 0)
			return;
		char[] c = c1.toCharArray();
		StringBuilder sb = new StringBuilder();
		int a = 0;
		for(int i = 0; i < c.length; i++)
		{
			if(c[i] == '\n')
			{
				switch(a)
				{
					case 0:
						anzahl = Double.parseDouble(sb.toString());
						vorher = anzahl;
						break;
					case 1:
						gek1 = Integer.parseInt(sb.toString());
						break;
					case 2:
						gek2 = Integer.parseInt(sb.toString());
						break;
					case 3:
						gek3 = Integer.parseInt(sb.toString());
						break;
				}
				sb = new StringBuilder();
				a++;
			}
			else
				sb.append(c[i]);
		}
	}

	private static final String[] ta = new String[]{"   ", "t  ", "mio", "mrd"};

	private static String d2s(double d, int len)
	{
		StringBuilder sb = new StringBuilder();
		while(d >= 1)
		{
			sb.append((int)(d % 10));
			d /= 10;
		}
		String s1 = sb.reverse().toString();
		int k = 0;
		while(s1.length() > len)
		{
			s1 = s1.substring(0, s1.length() - 3);
			k++;
		}
		if(k >= ta.length)
			return s1 + " E" + (k * 3);
		return s1 + " " + ta[k];
	}
}