using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Excercise_1
{
    public delegate double Funcs(double num);
    public class FunctionsContainer
    {
        //Dictonary of mission name and missions
        private Dictionary<string, Funcs> dic;

        /// <summary>
        /// Constructor
        /// </summary>
        public FunctionsContainer()
        {
            dic = new Dictionary<string, Funcs>();
        }

        /// <summary>
        /// Indexer
        /// </summary>
        /// <param name="name">the name of the function we want to find</param>
        /// <returns>the function with this name</returns>
        public Funcs this[string name]
        {
            get
            {
                //If not exist, create a function with this name that just retrun x
                if (!dic.ContainsKey(name))
                {
                    dic.Add(name, val => val);
                }
                return dic[name];
            }

            set
            {
                //If it's a new mission, add it to the dic
                if (!dic.ContainsKey(name))
                {
                    dic.Add(name, value);
                }
                //It's already exist, change the value that assgin to the mission name
                else
                {
                    dic[name] = value;
                }
            }
        }

        /// <summary>
        /// Return all the mission in the container
        /// </summary>
        /// <returns>all the missions in the dicanotry</returns>
        public List<string> getAllMissions()
        {
            return dic.Keys.ToList();
        }



    }
}