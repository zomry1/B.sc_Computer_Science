using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Excercise_1
{
    public class ComposedMission : IMission
    {
        private Funcs funcs; //Delegate that saves the functions
        public event EventHandler<double> OnCalculate;  // An Event of when a mission is activated
        public String Name { get; }
        public String Type { get; }

        /// <summary>
        /// Default constructor
        /// </summary>
        /// <param name="name">the name of the mission</param>
        public ComposedMission(string name)
        {
            Name = name;
            Type = "Composed";
        }

        /// <summary>
        /// Add missions to the delegate
        /// </summary>
        /// <param name="fun">the function we want to add</param>
        /// <returns>the mission with the new function</returns>
        public ComposedMission Add(Funcs fun)
        {
            this.funcs += fun;
            return this;
        }

        /// <summary>
        /// Calculate the value by the function in funcs, and call the OnCalculate event
        /// </summary>
        /// <param name="value">the value we pass to the functions</param>
        /// <returns>the value the functions return</returns>
        public double Calculate(double value)
        {

            var list = funcs.GetInvocationList();
            foreach(Funcs func in list)
            {
                value = func(value);
            }
            OnCalculate?.Invoke(this, value);
            return value;
        }
    }
}