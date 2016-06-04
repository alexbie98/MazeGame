package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import graphics.Shader;
import graphics.Texture;
import states.GameState;

public class Renderer {

	private Renderer(){}
	
	
	public static void render(GameState state){

		Map<Texture, Map<Shader, ArrayList<Renderable>>> objects = buildObjectMap(state);
		

		
		for(Texture t : objects.keySet())
		{
			t.bind();
			for(Shader s: objects.get(t).keySet())
			{
				s.enable();
				for(Renderable r: objects.get(t).get(s))
				{
					r.render();
				}
				s.disable();
			}
			t.unbind();
		}
	}
	
	private static Map<Texture, Map<Shader, ArrayList<Renderable>>> buildObjectMap(GameState state){
		
		Map<Texture, Map<Shader, ArrayList<Renderable>>> map = new HashMap<Texture, Map<Shader, ArrayList<Renderable>>>();

		for(Renderable r: state.getRenderables())
			{
				Texture t = r.texture;
				Shader s = r.shader;
				
				if(map.get(t) == null)
				{
					map.put(t, new HashMap<Shader, ArrayList<Renderable>>());
				}
				if(map.get(t).get(s) == null)
				{
					map.get(t).put(s, new ArrayList<Renderable>());
				}
				map.get(t).get(s).add(r);
			}

		
		

		return map;
	}
	
}
