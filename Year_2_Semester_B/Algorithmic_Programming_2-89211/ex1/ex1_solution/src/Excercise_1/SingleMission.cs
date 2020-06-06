using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Excercise_1
{
    public class SingleMission : IMission
    {
        private Funcs funcs; //Delegate that saves the functions
        public event EventHandler<double> OnCalculate;  // An Event of when a mission is activated
        public String Name { get; }
        public String Type { get; }

        /// <summary>
        /// Default constructor
        /// </summary>
        /// <param name="funcs">the function we want to add</param>
        /// <param name="name">the name of the mission</param>
        public SingleMission(Funcs funcs, string name)
        {
            Name = name;
            Type = "Single";
            this.funcs = funcs;
        }

        /// <summary>
        /// Calculate the value by the function in funcs, and call the OnCalculate event
        /// </summary>
        /// <param name="value">the value we want to pass to the function</param>
        /// <returns>the value returnred by the function</returns>
        public double Calculate(double value)
        {
            double returnValue = funcs(value);
            OnCalculate?.Invoke(this,returnValue);
            return returnValue;
        }
    }
}