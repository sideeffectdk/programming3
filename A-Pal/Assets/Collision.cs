using UnityEngine;
using System.Collections;

public class Collision : MonoBehaviour 
{
	


	void OnTriggerEnter (Collider other)
	{
		GameObject.Find ("Follow").GetComponent<Folloq> ().Collided = true;
		Debug.Log ("Something");
	}
}
