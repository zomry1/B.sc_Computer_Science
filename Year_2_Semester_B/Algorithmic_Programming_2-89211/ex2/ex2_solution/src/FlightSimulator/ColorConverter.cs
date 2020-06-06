using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;
using System.Windows.Media;

namespace FlightSimulator
{
    class ColorConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            //If target type is not brush
            if (targetType != typeof(Brush))
            {
                throw new InvalidOperationException("Must convert to brush!");
            }
            //Cast value to bool
            bool empty = (bool) value;
            //If true - return red else return white
            return empty ? Brushes.Red : Brushes.White;
        }
        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
